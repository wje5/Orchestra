package com.zonesoft.orchestra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zonesoft.orchestra.inst.InstrumentManager;
import com.zonesoft.orchestra.item.ItemLoader;
import com.zonesoft.orchestra.network.NetworkHandler;

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
		event.enqueueWork(NetworkHandler::init);
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		ClientMidiHandler.init();
		InstrumentManager.registerHandlers();
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(RegistryEvent.Register<Block> event) {
			LOGGER.info("HELLO from Register Block");
		}
	}
}