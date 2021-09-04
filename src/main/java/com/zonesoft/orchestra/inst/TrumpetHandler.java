package com.zonesoft.orchestra.inst;

import javax.sound.midi.ShortMessage;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.item.ItemLoader;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class TrumpetHandler extends InstrumentHandler {
	private int basePitch = -1;

	public TrumpetHandler() {
		super(new int[] { GLFW.GLFW_KEY_8, GLFW.GLFW_KEY_9, GLFW.GLFW_KEY_0, GLFW.GLFW_KEY_V, GLFW.GLFW_KEY_B,
				GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M, GLFW.GLFW_KEY_G, GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_J, GLFW.GLFW_KEY_K });
	}

	@Override
	public boolean isReady(PlayerEntity player) {
		boolean flag = player.getItemInHand(Hand.MAIN_HAND).getItem() == ItemLoader.trumpet.get()
				&& player.getItemInHand(Hand.OFF_HAND).isEmpty();
		if (flag) {
			if (!isRunning()) {
				start();
			}
		} else {
			if (isRunning()) {
				stop();
			}
		}
		return flag;
	}

	@Override
	protected void onStart() {
		sendMessage(ShortMessage.PROGRAM_CHANGE, 56, 0);
	}

	@Override
	public void onStateChange(int index, boolean isPress) {
		System.out.println(index + "|" + isPress);
		if (index <= 2) {
			if (basePitch >= 0) {
				sendMessage(ShortMessage.NOTE_OFF, calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)), 0);
				sendMessage(ShortMessage.NOTE_ON, calcPitch(basePitch, index == 0 ? isPress : isPressed(0),
						index == 1 ? isPress : isPressed(1), index == 2 ? isPress : isPressed(2)), 65);
			}
			return;
		}
		int pitch = 0;
		switch (index) {
		case 3:
			pitch = 63;
			break;
		case 4:
			pitch = 70;
			break;
		case 5:
			pitch = 77;
			break;
		case 6:
			pitch = 82;
			break;
		case 7:
			pitch = 86;
			break;
		case 8:
			pitch = 89;
			break;
		case 9:
			pitch = 94;
			break;
		case 10:
			pitch = 98;
			break;
		}
		if (isPress) {
			if (basePitch != pitch) {
				if (basePitch >= 0) {
					sendMessage(ShortMessage.NOTE_OFF, calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)),
							0);
				}
				sendMessage(ShortMessage.NOTE_ON, calcPitch(pitch, isPressed(0), isPressed(1), isPressed(2)), 65);
				basePitch = pitch;
			}
		} else if (basePitch == pitch) {
			sendMessage(ShortMessage.NOTE_OFF, calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)), 0);
			basePitch = -1;
		}
	}

	private int calcPitch(int base, boolean button1, boolean button2, boolean button3) {
		if (button1) {
			base -= 2;
		}
		if (button2) {
			base--;
		}
		if (button3) {
			base -= 3;
		}
		return base;
	}
}
