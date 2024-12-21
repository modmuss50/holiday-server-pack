package holiday.server.block;

import holiday.server.CommonEntrypoint;
import io.github.haykam821.columns.block.ColumnBlock;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public record ColumnData(Identifier id, Block block, Item item, Block wall) {
    public static final ColumnData[] COLUMNS = new ColumnData[] {
        ColumnData.fromPyrite("white_brick"),
        ColumnData.fromPyrite("white_terracotta_brick"),
        ColumnData.fromPyrite("orange_brick"),
        ColumnData.fromPyrite("orange_terracotta_brick"),
        ColumnData.fromPyrite("magenta_brick"),
        ColumnData.fromPyrite("magenta_terracotta_brick"),
        ColumnData.fromPyrite("light_blue_brick"),
        ColumnData.fromPyrite("light_blue_terracotta_brick"),
        ColumnData.fromPyrite("yellow_brick"),
        ColumnData.fromPyrite("yellow_terracotta_brick"),
        ColumnData.fromPyrite("lime_brick"),
        ColumnData.fromPyrite("lime_terracotta_brick"),
        ColumnData.fromPyrite("pink_brick"),
        ColumnData.fromPyrite("pink_terracotta_brick"),
        ColumnData.fromPyrite("gray_brick"),
        ColumnData.fromPyrite("gray_terracotta_brick"),
        ColumnData.fromPyrite("light_gray_brick"),
        ColumnData.fromPyrite("light_gray_terracotta_brick"),
        ColumnData.fromPyrite("cyan_brick"),
        ColumnData.fromPyrite("cyan_terracotta_brick"),
        ColumnData.fromPyrite("purple_brick"),
        ColumnData.fromPyrite("purple_terracotta_brick"),
        ColumnData.fromPyrite("blue_brick"),
        ColumnData.fromPyrite("blue_terracotta_brick"),
        ColumnData.fromPyrite("brown_brick"),
        ColumnData.fromPyrite("brown_terracotta_brick"),
        ColumnData.fromPyrite("green_brick"),
        ColumnData.fromPyrite("green_terracotta_brick"),
        ColumnData.fromPyrite("red_brick"),
        ColumnData.fromPyrite("red_terracotta_brick"),
        ColumnData.fromPyrite("black_brick"),
        ColumnData.fromPyrite("black_terracotta_brick"),
        ColumnData.fromPyrite("glow_brick"),
        ColumnData.fromPyrite("glow_terracotta_brick"),
        ColumnData.fromPyrite("dragon_brick"),
        ColumnData.fromPyrite("dragon_terracotta_brick"),
        ColumnData.fromPyrite("star_brick"),
        ColumnData.fromPyrite("star_terracotta_brick"),
        ColumnData.fromPyrite("honey_brick"),
        ColumnData.fromPyrite("honey_terracotta_brick"),
        ColumnData.fromPyrite("nostalgia_brick"),
        ColumnData.fromPyrite("nostalgia_terracotta_brick"),
        ColumnData.fromPyrite("rose_brick"),
        ColumnData.fromPyrite("rose_terracotta_brick"),
        ColumnData.fromPyrite("poisonous_brick"),
        ColumnData.fromPyrite("poisonous_terracotta_brick"),
        ColumnData.fromPyrite("cobblestone_brick"),
        ColumnData.fromPyrite("mossy_cobblestone_brick"),
        ColumnData.fromPyrite("sandstone_brick"),
        ColumnData.fromPyrite("smooth_stone_brick"),
        ColumnData.fromPyrite("mossy_smooth_stone_brick"),
        ColumnData.fromPyrite("granite_brick"),
        ColumnData.fromPyrite("mossy_granite_brick"),
        ColumnData.fromPyrite("andesite_brick"),
        ColumnData.fromPyrite("mossy_andesite_brick"),
        ColumnData.fromPyrite("diorite_brick"),
        ColumnData.fromPyrite("mossy_diorite_brick"),
        ColumnData.fromPyrite("calcite_brick"),
        ColumnData.fromPyrite("mossy_calcite_brick"),
        ColumnData.fromPyrite("mossy_tuff_brick"),
        ColumnData.fromPyrite("mossy_deepslate_brick"),
        ColumnData.fromPyrite("charred_nether_brick"),
        ColumnData.fromPyrite("blue_nether_brick"),
        ColumnData.fromPyrite("cut_iron"),
        ColumnData.fromPyrite("smooth_iron"),
        ColumnData.fromPyrite("cut_gold"),
        ColumnData.fromPyrite("smooth_gold"),
        ColumnData.fromPyrite("cut_emerald"),
        ColumnData.fromPyrite("smooth_emerald"),
        ColumnData.fromPyrite("cut_diamond"),
        ColumnData.fromPyrite("smooth_diamond"),
        ColumnData.fromPyrite("cut_netherite"),
        ColumnData.fromPyrite("smooth_netherite"),
        ColumnData.fromPyrite("cut_quartz"),
        ColumnData.fromPyrite("smooth_quartz"),
        ColumnData.fromPyrite("cut_amethyst"),
        ColumnData.fromPyrite("smooth_amethyst"),
        ColumnData.fromPyrite("cut_lapis"),
        ColumnData.fromPyrite("smooth_lapis"),
        ColumnData.fromPyrite("cut_redstone"),
        ColumnData.fromPyrite("smooth_redstone"),
        ColumnData.fromPyrite("cut_copper"),
        ColumnData.fromPyrite("smooth_copper"),
        ColumnData.fromPyrite("cut_exposed_copper"),
        ColumnData.fromPyrite("smooth_exposed_copper"),
        ColumnData.fromPyrite("cut_weathered_copper"),
        ColumnData.fromPyrite("smooth_weathered_copper"),
        ColumnData.fromPyrite("cut_oxidized_copper"),
        ColumnData.fromPyrite("smooth_oxidized_copper"),
        ColumnData.fromPyrite("terracotta_brick_wall")
    };

    public void registerBlock() {
        Registry.register(Registries.BLOCK, this.id, this.block);
    }

    public void registerItem() {
        Registry.register(Registries.ITEM, this.id, this.item);
    }

    public void modifyEntries(FabricItemGroupEntries entries) {
        entries.addBefore(this.wall, this.item);
    }

    static ColumnData from(String path, Block base, Block wall) {
        Identifier id = CommonEntrypoint.identifier(path + "_column");

        Block.Settings blockSettings = Block.Settings.copy(base)
            .registryKey(RegistryKey.of(RegistryKeys.BLOCK, id));

        Block block = new ColumnBlock(blockSettings);

        Item.Settings itemSettings = new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id))
            .useBlockPrefixedTranslationKey();

        Item item = new BlockItem(block, itemSettings);

        return new ColumnData(id, block, item, wall);
    }

    static ColumnData fromPyrite(String path) {
        Identifier baseId = Identifier.of("pyrite", path);
        Block base = Registries.BLOCK.get(baseId);

        Identifier wallId = Identifier.of("pyrite", path + "_wall");
        Block wall = Registries.BLOCK.get(wallId);

        return ColumnData.from(path, base, wall);
    }
}
