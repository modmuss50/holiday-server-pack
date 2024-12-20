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
    public static final TagKey<BannerPattern> FABRIC_PATTERN_ITEM_TAG = patternTagOf("pattern_item/fabric");
    public static final Item FABRIC_PATTERN_ITEM = new BannerPatternItem(FABRIC_PATTERN_ITEM_TAG,
            new Item.Settings().maxCount(1).registryKey(registryKeyItem("fabric_banner_pattern")));

    private static TagKey<BannerPattern> patternTagOf(String id) {
        return TagKey.of(RegistryKeys.BANNER_PATTERN, locate(id));
    }

    public static RegistryKey<Item> registryKeyItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, locate(id));
    }

    public static Identifier locate(String id) {
        return Identifier.of("holiday-server-mod", id);
    }

    public static void register() {
        Registry.register(Registries.ITEM, locate("fabric_banner_pattern"), FABRIC_PATTERN_ITEM);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) ->
                itemGroup.addAfter(Items.MOJANG_BANNER_PATTERN, FABRIC_PATTERN_ITEM));
    }
}
