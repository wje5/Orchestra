package com.zonesoft.orchestra.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	public static SimpleChannel instance;
	public static final String VERSION = "1.0";
	private static int nextId = 0;

	public static void init() {
		instance = NetworkRegistry.newSimpleChannel(new ResourceLocation("orchestra:network"), () -> VERSION,
				(version) -> version.equals(VERSION), (version) -> version.equals(VERSION));
		instance.messageBuilder(MessageMidiToServer.class, nextId++).encoder(MessageMidiToServer::toBytes)
				.decoder(MessageMidiToServer::new).consumer(MessageOrchestra::run).add();
		instance.messageBuilder(MessageMidiToClient.class, nextId++).encoder(MessageMidiToClient::toBytes)
				.decoder(MessageMidiToClient::new).consumer(MessageOrchestra::run).add();
	}
}
