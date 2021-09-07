package com.zonesoft.orchestra.network;

import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageOrchestra {
	public MessageOrchestra(ByteBuf buf) {
		fromBytes(buf);
	}

	public MessageOrchestra() {

	}

	public void fromBytes(ByteBuf buf) {

	}

	public void toBytes(ByteBuf buf) {

	}

	public void run(Supplier<NetworkEvent.Context> ctx) {

	}
}
