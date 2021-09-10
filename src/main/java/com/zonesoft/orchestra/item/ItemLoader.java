package com.zonesoft.orchestra.item;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemLoader {
	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "orchestra");
	public static RegistryObject<Item> trumpet = ITEMS.register("trumpet", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> trumbone = ITEMS.register("trumbone", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> tuba = ITEMS.register("tuba", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> french_horn = ITEMS.register("french_horn",
			() -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> flute = ITEMS.register("flute", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> piccolo = ITEMS.register("piccolo", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> oboe = ITEMS.register("oboe", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> bassoon = ITEMS.register("bassoon", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> clarinet = ITEMS.register("clarinet", () -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> soprano_saxphone = ITEMS.register("soprano_saxphone",
			() -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> alto_saxphone = ITEMS.register("alto_saxphone",
			() -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> baritone_saxphone = ITEMS.register("baritone_saxphone",
			() -> new OrchestraItem(new Properties()));
	public static RegistryObject<Item> snare_drum = ITEMS.register("snare_drum",
			() -> new OrchestraItem(new Properties()));
}
