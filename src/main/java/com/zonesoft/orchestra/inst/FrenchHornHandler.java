package com.zonesoft.orchestra.inst;

import javax.sound.midi.ShortMessage;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.item.ItemLoader;

import net.minecraft.entity.player.PlayerEntity;

public class FrenchHornHandler extends InstrumentHandler {
	private int basePitch = -1, dynamic = 3;

	public FrenchHornHandler() {
		super(new int[] { GLFW.GLFW_KEY_8, GLFW.GLFW_KEY_9, GLFW.GLFW_KEY_0, GLFW.GLFW_KEY_V, GLFW.GLFW_KEY_B,
				GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M, GLFW.GLFW_KEY_G, GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_J, GLFW.GLFW_KEY_K,
				GLFW.GLFW_KEY_Y, GLFW.GLFW_KEY_U, GLFW.GLFW_KEY_I, GLFW.GLFW_KEY_O, GLFW.GLFW_KEY_P,
				GLFW.GLFW_KEY_LEFT_BRACKET, GLFW.GLFW_KEY_RIGHT_BRACKET, GLFW.GLFW_KEY_BACKSLASH });
	}

	@Override
	public boolean isReady(PlayerEntity player) {
		boolean flag = player.getHeldItemMainhand().getItem() == ItemLoader.french_horn.get()
				&& player.getHeldItemOffhand().isEmpty();
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
		sendMessage(ShortMessage.PROGRAM_CHANGE, 0, 60, 0);
	}

	@Override
	protected void onStop() {
		if (basePitch >= 0) {
			sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)), 0);
		}
	}

	@Override
	public void onStateChange(int index, boolean isPress) {
		if (index <= 2) {
			if (!isPress) {
				return;
			}
			if (basePitch >= 0) {
				sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)),
						0);
				sendMessage(
						ShortMessage.NOTE_ON, 0, calcPitch(basePitch, index == 0 ? isPress : isPressed(0),
								index == 1 ? isPress : isPressed(1), index == 2 ? isPress : isPressed(2)),
						dynamic * 10 + 57);
			}
			return;
		}
		if (index >= 11) {
			int d = index - 11;
			if (d != dynamic) {
				dynamic = d;
				if (basePitch >= 0) {
					sendMessage(ShortMessage.NOTE_OFF, 0,
							calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)), 0);
					sendMessage(ShortMessage.NOTE_ON, 0, calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)),
							dynamic * 10 + 57);
				}
			}
			return;
		}
		int pitch = 0;
		switch (index) {
		case 3:
			pitch = 34;
			break;
		case 4:
			pitch = 41;
			break;
		case 5:
			pitch = 48;
			break;
		case 6:
			pitch = 53;
			break;
		case 7:
			pitch = 57;
			break;
		case 8:
			pitch = 60;
			break;
		case 9:
			pitch = 65;
			break;
		case 10:
			pitch = 69;
			break;
		}
		if (isPress) {
			if (basePitch != pitch) {
				if (basePitch >= 0) {
					sendMessage(ShortMessage.NOTE_OFF, 0,
							calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)), 0);
				}
				sendMessage(ShortMessage.NOTE_ON, 0, calcPitch(pitch, isPressed(0), isPressed(1), isPressed(2)),
						dynamic * 10 + 57);
				basePitch = pitch;
			}
		} else if (basePitch == pitch) {
			sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2)), 0);
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

	@Override
	public int getDynamic() {
		return dynamic;
	}
}
