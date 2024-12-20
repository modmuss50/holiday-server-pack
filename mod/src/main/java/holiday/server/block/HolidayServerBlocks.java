package holiday.server.block;

import holiday.server.CommonEntrypoint;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public final class HolidayServerBlocks {
    public static final Block TINY_POTATO = register("tiny_potato", settings -> new TinyPotatoBlock(settings
            .mapColor(MapColor.RAW_IRON_PINK)
            .breakInstantly()
            .nonOpaque()
            .sounds(BlockSoundGroup.CROP)));

    private HolidayServerBlocks() {
    }

    public static void register() {
        Registry.register(Registries.BLOCK_TYPE, CommonEntrypoint.identifier("tiny_potato"), TinyPotatoBlock.CODEC);
    }

    public static Block register(String path, Function<Block.Settings, Block> factory) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, CommonEntrypoint.identifier(path));

        Block.Settings settings = Block.Settings.create().registryKey(key);
        Block block = factory.apply(settings);

        return Registry.register(Registries.BLOCK, key, block);
    }
}
