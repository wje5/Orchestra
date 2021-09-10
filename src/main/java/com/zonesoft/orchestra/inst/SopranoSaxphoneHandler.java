package com.zonesoft.orchestra.inst;

import javax.sound.midi.ShortMessage;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.item.ItemLoader;

import net.minecraft.entity.player.PlayerEntity;

public class SopranoSaxphoneHandler extends InstrumentHandler {
	private int basePitch = -1, dynamic = 3;

	public SopranoSaxphoneHandler() {
		super(new int[] { GLFW.GLFW_KEY_4, GLFW.GLFW_KEY_R, GLFW.GLFW_KEY_T, GLFW.GLFW_KEY_6, GLFW.GLFW_KEY_Y,
				GLFW.GLFW_KEY_7, GLFW.GLFW_KEY_U, GLFW.GLFW_KEY_I, GLFW.GLFW_KEY_9, GLFW.GLFW_KEY_O, GLFW.GLFW_KEY_0,
				GLFW.GLFW_KEY_P, GLFW.GLFW_KEY_MINUS, GLFW.GLFW_KEY_LEFT_BRACKET, GLFW.GLFW_KEY_RIGHT_BRACKET,
				GLFW.GLFW_KEY_SPACE, GLFW.GLFW_KEY_RIGHT_ALT, GLFW.GLFW_KEY_C, GLFW.GLFW_KEY_V, GLFW.GLFW_KEY_B,
				GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M, GLFW.GLFW_KEY_COMMA, GLFW.GLFW_KEY_PERIOD, GLFW.GLFW_KEY_SLASH });
	}

	@Override
	public boolean isReady(PlayerEntity player) {
		boolean flag = player.getHeldItemMainhand().getItem() == ItemLoader.soprano_saxphone.get()
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
		sendMessage(ShortMessage.PROGRAM_CHANGE, 0, 64, 0);
	}

	@Override
	protected void onStop() {
		if (basePitch >= 0) {
			sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(15), isPressed(16)), 0);
		}
	}

	@Override
	public void onStateChange(int index, boolean isPress) {
		if (index >= 17) {
			if (!isPress) {
				return;
			}
			int d = index - 17;
			if (d != dynamic) {
				dynamic = d;
				if (basePitch >= 0) {
					sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(15), isPressed(16)), 0);
					sendMessage(ShortMessage.NOTE_ON, 0, calcPitch(basePitch, isPressed(15), isPressed(16)),
							dynamic * 10 + 57);
				}
			}
			return;
		}
		if (index >= 15) {
			if (basePitch >= 0) {
				sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(15), isPressed(16)), 0);
				sendMessage(ShortMessage.NOTE_ON, 0, calcPitch(basePitch, index == 15 ? isPress : isPressed(15),
						index == 16 ? isPress : isPressed(16)), dynamic * 10 + 57);
			}
			return;
		}
		int pitch = 58 + index;
		if (isPress) {
			if (basePitch != pitch) {
				if (basePitch >= 0) {
					sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(15), isPressed(16)), 0);
				}
				sendMessage(ShortMessage.NOTE_ON, 0, calcPitch(pitch, isPressed(15), isPressed(16)), dynamic * 10 + 57);
				basePitch = pitch;
			}
		} else if (basePitch == pitch) {
			sendMessage(ShortMessage.NOTE_OFF, 0, calcPitch(basePitch, isPressed(15), isPressed(16)), 0);
			basePitch = -1;
		}
	}

	private int calcPitch(int base, boolean button1, boolean button2) {
		return button2 ? base + 24 : button1 ? base + 12 : base;
	}

	@Override
	public int getDynamic() {
		return dynamic;
	}
}
