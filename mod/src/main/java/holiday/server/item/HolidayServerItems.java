package holiday.server.item;

import holiday.server.CommonEntrypoint;
import holiday.server.block.HolidayServerBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Function;

public final class HolidayServerItems {
    public static final Item TINY_POTATO = register("tiny_potato", settings -> new BlockItem(HolidayServerBlocks.TINY_POTATO, settings
            .useBlockPrefixedTranslationKey()
            .equippableUnswappable(EquipmentSlot.HEAD)));
    public static final Item FABRIC_PATTERN_ITEM = register("fabric_banner_pattern", settings -> new BannerPatternItem(patternTagOf("pattern_item/fabric"),
            settings.maxCount(1)));
    public static final Item TATER_PATTERN_ITEM = register("tater_banner_pattern", settings -> new BannerPatternItem(patternTagOf("pattern_item/tater"),
            settings.maxCount(1)));

    private HolidayServerItems() {
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) ->
                itemGroup.addAfter(Items.MOJANG_BANNER_PATTERN, FABRIC_PATTERN_ITEM, TATER_PATTERN_ITEM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.addBefore(Items.SKELETON_SKULL, TINY_POTATO);
        });
    }

    public static Item register(String path, Function<Item.Settings, Item> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, CommonEntrypoint.identifier(path));

        Item.Settings settings = new Item.Settings().registryKey(key);
        Item item = factory.apply(settings);

        return Registry.register(Registries.ITEM, key, item);
    }

    private static TagKey<BannerPattern> patternTagOf(String id) {
        return TagKey.of(RegistryKeys.BANNER_PATTERN, CommonEntrypoint.identifier(id));
    }
}
