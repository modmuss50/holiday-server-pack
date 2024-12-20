package holiday.server.item;

import holiday.server.CommonEntrypoint;
import holiday.server.block.HolidayServerBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public final class HolidayServerItems {
    public static final Item TINY_POTATO = register("tiny_potato", settings -> new BlockItem(HolidayServerBlocks.TINY_POTATO, settings
            .useBlockPrefixedTranslationKey()
            .equippableUnswappable(EquipmentSlot.HEAD)));

    private HolidayServerItems() {
    }

    public static void register() {
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
}
