package com.zonesoft.orchestra.item;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemLoader {
	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "orchestra");
	public static RegistryObject<Item> trumpet = ITEMS.register("trumpet",
			() -> new OrchestraItem(new Properties()));
}
