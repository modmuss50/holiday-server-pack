package holiday.server;

import holiday.server.block.HolidayServerBlocks;
import holiday.server.item.HolidayServerItems;
import net.fabricmc.api.ModInitializer;
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
import net.minecraft.server.network.ServerConfigurationNetworkHandler;
import net.minecraft.server.network.ServerPlayerConfigurationTask;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.net.URI;
import java.util.Optional;
import java.util.function.Consumer;

public class CommonEntrypoint implements ModInitializer {
    private static final String MOD_ID = "holiday-server-mod";

    public static final String CURRENT_VERSION = FabricLoader.getInstance()
            .getModContainer(MOD_ID)
            .get()
            .getMetadata()
            .getVersion()
            .getFriendlyString();

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
