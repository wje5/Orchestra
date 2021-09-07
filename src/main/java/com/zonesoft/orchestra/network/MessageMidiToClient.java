package com.zonesoft.orchestra.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.zonesoft.orchestra.ClientMidiHandler;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageMidiToClient extends MessageOrchestra {
	private UUID player;
	private int command, channel, pitch, keystroke;

	public MessageMidiToClient(ByteBuf buf) {
		super(buf);
	}

	public MessageMidiToClient(UUID player, int command, int channel, int pitch, int keystroke) {
		this.player = player;
		this.command = command;
		this.channel = channel;
		this.pitch = pitch;
		this.keystroke = keystroke;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		player = new UUID(buf.readLong(), buf.readLong());
		command = buf.readByte() & 0xFF;
		channel = buf.readByte() & 0xF;
		pitch = buf.readByte() & 0x7F;
		keystroke = buf.readByte() & 0x7F;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeLong(player.getMostSignificantBits());
		buf.writeLong(player.getLeastSignificantBits());
		buf.writeByte(command);
		buf.writeByte(channel);
		buf.writeByte(pitch);
		buf.writeByte(keystroke);
	}

	@Override
	public void run(Supplier<Context> ctx) {
		super.run(ctx);
		ClientMidiHandler.onMessage(player, command, channel, pitch, keystroke);
	}
}
