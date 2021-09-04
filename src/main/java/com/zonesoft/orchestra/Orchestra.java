package com.zonesoft.orchestra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zonesoft.orchestra.item.ItemLoader;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("orchestra")
public class Orchestra {
	public static final Logger LOGGER = LogManager.getLogger();

	public Orchestra() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		MinecraftForge.EVENT_BUS.register(this);
		ItemLoader.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	private void setup(final FMLCommonSetupEvent event) {
		// PREINIT
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		ClientMidiHandler.init();
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			LOGGER.info("HELLO from Register Block");
		}
	}
}