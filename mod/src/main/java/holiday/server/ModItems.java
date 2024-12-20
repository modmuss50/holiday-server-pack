package holiday.server;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item FABRIC_PATTERN_ITEM = addBannerPatternItem("fabric");
    public static final Item TATER_PATTERN_ITEM = addBannerPatternItem("tater");

    private static TagKey<BannerPattern> patternTagOf(String id) {
        return TagKey.of(RegistryKeys.BANNER_PATTERN, locate(id));
    }

    public static RegistryKey<Item> registryKeyItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, locate(id));
    }

    public static Identifier locate(String id) {
        return Identifier.of("holiday-server-mod", id);
    }

    public static Item addBannerPatternItem(String id) {
        return new BannerPatternItem(patternTagOf("pattern_item/%s".formatted(id)),
                new Item.Settings().maxCount(1).registryKey(registryKeyItem("%s_banner_pattern".formatted(id))));
    }

    public static void register() {
        Registry.register(Registries.ITEM, locate("fabric_banner_pattern"), FABRIC_PATTERN_ITEM);
        Registry.register(Registries.ITEM, locate("tater_banner_pattern"), TATER_PATTERN_ITEM);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) ->
                itemGroup.addAfter(Items.MOJANG_BANNER_PATTERN, FABRIC_PATTERN_ITEM, TATER_PATTERN_ITEM));
    }
}
