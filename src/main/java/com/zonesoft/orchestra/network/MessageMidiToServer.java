package com.zonesoft.orchestra.network;

import java.util.UUID;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.PacketDistributor;

public class MessageMidiToServer extends MessageOrchestra {
	private int command, channel, pitch, keystroke;

	public MessageMidiToServer(ByteBuf buf) {
		super(buf);
	}

	public MessageMidiToServer(int command, int channel, int pitch, int keystroke) {
		this.command = command;
		this.channel = channel;
		this.pitch = pitch;
		this.keystroke = keystroke;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		command = buf.readByte() & 0xFF;
		channel = buf.readByte() & 0xF;
		pitch = buf.readByte() & 0x7F;
		keystroke = buf.readByte() & 0x7F;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeByte(command);
		buf.writeByte(channel);
		buf.writeByte(pitch);
		buf.writeByte(keystroke);
	}

	@Override
	public void run(Supplier<Context> ctx) {
		super.run(ctx);
		ServerPlayerEntity player = ctx.get().getSender();
		UUID uuid = player.getUniqueID();
		player.world.getPlayers().forEach(e -> {
			if (e.getUniqueID() != uuid) {
				NetworkHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) e),
						new MessageMidiToClient(uuid, command, channel, pitch, keystroke));
			}
		});
	}
}
