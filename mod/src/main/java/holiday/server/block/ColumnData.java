package holiday.server.block;

import com.google.common.collect.Sets;
import holiday.server.CommonEntrypoint;
import io.github.haykam821.columns.block.ColumnBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Set;

public record ColumnData(Identifier id, RegistryKey<Block> base, RegistryKey<Block> wall) {
    public static final Set<ColumnData> UNREGISTERED_COLUMNS = Sets.newHashSet(
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
        ColumnData.fromPyrite("smooth_quartz", "minecraft:smooth_quartz"),
        ColumnData.fromPyrite("cut_amethyst"),
        ColumnData.fromPyrite("smooth_amethyst"),
        ColumnData.fromPyrite("cut_lapis"),
        ColumnData.fromPyrite("smooth_lapis"),
        ColumnData.fromPyrite("cut_redstone"),
        ColumnData.fromPyrite("smooth_redstone"),
        ColumnData.fromPyrite("cut_copper", "minecraft:cut_copper"),
        ColumnData.fromPyrite("smooth_copper"),
        ColumnData.fromPyrite("cut_exposed_copper", "minecraft:exposed_cut_copper"),
        ColumnData.fromPyrite("smooth_exposed_copper"),
        ColumnData.fromPyrite("cut_weathered_copper", "minecraft:weathered_cut_copper"),
        ColumnData.fromPyrite("smooth_weathered_copper"),
        ColumnData.fromPyrite("cut_oxidized_copper", "minecraft:oxidized_cut_copper"),
        ColumnData.fromPyrite("smooth_oxidized_copper"),
        ColumnData.fromPyrite("terracotta_brick")
    );

    public boolean register() {
        Block base = Registries.BLOCK.get(this.base);
        if (base == null) return false;

        Block wall = Registries.BLOCK.get(this.wall);
        if (wall == null) return false;

        Block.Settings blockSettings = Block.Settings.copy(base)
            .registryKey(RegistryKey.of(RegistryKeys.BLOCK, id));

        Block block = new ColumnBlock(blockSettings);
        Registry.register(Registries.BLOCK, this.id, block);

        Item.Settings itemSettings = new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, id))
            .useBlockPrefixedTranslationKey();

        Item item = new BlockItem(block, itemSettings);
        Registry.register(Registries.ITEM, this.id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addBefore(wall, item);
        });

        return true;
    }

    static ColumnData from(String path, RegistryKey<Block> base, RegistryKey<Block> wall) {
        Identifier id = CommonEntrypoint.identifier(path + "_column");
        return new ColumnData(id, base, wall);
    }

    static ColumnData fromPyrite(String path) {
        String blockPath;
        if (path.contains("brick"))
            blockPath = path+ "s";
        else
            blockPath = path;
        return fromPyrite(path,"pyrite:"+ blockPath);
    }

    static ColumnData fromPyrite(String path, String baseId) {
        RegistryKey<Block> base = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(baseId));

        Identifier wallId = Identifier.of("pyrite", path + "_wall");
        RegistryKey<Block> wall = RegistryKey.of(RegistryKeys.BLOCK, wallId);

        return ColumnData.from(path, base, wall);
    }
}
