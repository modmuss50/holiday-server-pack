package holiday.server;

import com.mojang.serialization.Codec;
import holiday.server.block.HolidayServerBlocks;
import holiday.server.item.HolidayServerItems;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerConfigurationNetworkHandler;
import net.minecraft.server.network.ServerPlayerConfigurationTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class CommonEntrypoint implements ModInitializer {
    private static final String MOD_ID = "holiday-server-mod";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final String CURRENT_VERSION = FabricLoader.getInstance()
            .getModContainer(MOD_ID)
            .get()
            .getMetadata()
            .getVersion()
            .getFriendlyString();

    private static final AttachmentType<Boolean> ANIMALS_REGENERATED_CHUNK_TYPE = AttachmentRegistry.create(
            identifier("animals_regenerated"),
            builder -> builder.initializer(() -> Boolean.FALSE).persistent(Codec.BOOL)
    );

    private static final int CHUNK_RESPAWN_RADIUS_BLOCKS = 2000;
    private static final int SCAN_RADIUS_CHUNKS = 3;
    private static final LongSet CHECKED_CHUNKS = new LongOpenHashSet();

    @Override
    public void onInitialize() {
        HolidayServerBlocks.register();
        HolidayServerItems.register();

        PayloadTypeRegistry.configurationS2C().register(RequestVersionPayload.ID, RequestVersionPayload.PACKET_CODEC);
        PayloadTypeRegistry.configurationC2S().register(VersionResponsePayload.ID, VersionResponsePayload.PACKET_CODEC);

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            if (ServerConfigurationNetworking.canSend(handler, RequestVersionPayload.ID)) {
                handler.addTask(new CheckVersionTask());
            } else {
                disconnect(handler, "unknown");
            }
        });

        ServerConfigurationNetworking.registerGlobalReceiver(VersionResponsePayload.ID, (payload, context) -> {
            if (!CURRENT_VERSION.equals(payload.version())) {
                disconnect(context.networkHandler(), payload.version());
                return;
            }

            context.networkHandler().completeTask(CheckVersionTask.KEY);
        });

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world.getTime() % 20 != 0) {
                return;
            }

            if (world.getRegistryKey() != World.OVERWORLD) {
                return;
            }

            for (ServerPlayerEntity player : world.getPlayers()) {
                // We only need to fix chunks within the initial world border
                if (player.getX() < -CHUNK_RESPAWN_RADIUS_BLOCKS || player.getX() > CHUNK_RESPAWN_RADIUS_BLOCKS || player.getZ() < -CHUNK_RESPAWN_RADIUS_BLOCKS || player.getZ()  > CHUNK_RESPAWN_RADIUS_BLOCKS) {
                    return;
                }

                for (int x = -SCAN_RADIUS_CHUNKS; x < SCAN_RADIUS_CHUNKS; x++) {
                    for (int z = -SCAN_RADIUS_CHUNKS; z < SCAN_RADIUS_CHUNKS; z++) {
                        long chunkLongPos = ChunkPos.toLong(player.getChunkPos().x + x, player.getChunkPos().z + z);

                        if (!world.isChunkLoaded(chunkLongPos)) {
                            continue;
                        }

                        if (!CHECKED_CHUNKS.add(chunkLongPos)) {
                            continue;
                        }

                        ChunkPos chunkPos = new ChunkPos(chunkLongPos);
                        WorldChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);

                        if (Boolean.TRUE.equals(chunk.getAttached(ANIMALS_REGENERATED_CHUNK_TYPE))) {
                            return;
                        }

                        RegistryEntry<Biome> registryEntry = world.getBiome(chunkPos.getStartPos().withY(world.getTopYInclusive()));
                        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
                        chunkRandom.setPopulationSeed(world.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
                        SpawnHelper.populateEntities(world, registryEntry, chunkPos, chunkRandom);
                        chunk.setAttached(ANIMALS_REGENERATED_CHUNK_TYPE, true);

                        LOGGER.debug("Regenerated animals in chunk {}", chunkPos);
                    }
                }
            }
        });
    }

    private static void disconnect(ServerConfigurationNetworkHandler handler, String currentVersion) {
        MutableText text = Text.literal("You must have the same version of the modpack installed to play on this server.");
        text.append("\n").append(Text.literal("Download the following version: ")).append(Text.literal(CURRENT_VERSION).formatted(Formatting.YELLOW));
        text.append("\n").append(Text.literal("You currently have version: ")).append(Text.literal(currentVersion).formatted(Formatting.RED));
        handler.disconnect(new DisconnectionInfo(
            text,
            Optional.empty(),
            Optional.of(URI.create("https://github.com/modmuss50/holiday-server-pack/commit/%s".formatted(CURRENT_VERSION)))
        ));
    }

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public record CheckVersionTask() implements ServerPlayerConfigurationTask {
        public static final Key KEY = new Key(RequestVersionPayload.ID.toString());

        @Override
        public void sendPacket(Consumer<Packet<?>> sender) {
            sender.accept(ServerConfigurationNetworking.createS2CPacket(new RequestVersionPayload()));
        }

        @Override
        public Key getKey() {
            return KEY;
        }
    }
    
    public record RequestVersionPayload() implements CustomPayload {
        public static final CustomPayload.Id<RequestVersionPayload> ID = new CustomPayload.Id<>(Identifier.of("holiday-server-mod", "request_version"));
        public static final PacketCodec<PacketByteBuf, RequestVersionPayload> PACKET_CODEC = PacketCodec.unit(new RequestVersionPayload());

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public record VersionResponsePayload(String version) implements CustomPayload {
        public static final CustomPayload.Id<VersionResponsePayload> ID = new CustomPayload.Id<>(Identifier.of("holiday-server-mod", "version_response"));
        public static final PacketCodec<PacketByteBuf, VersionResponsePayload> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, VersionResponsePayload::version,
            VersionResponsePayload::new
        );

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
