package com.zonesoft.orchestra.inst;

import javax.sound.midi.ShortMessage;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.item.ItemLoader;

import net.minecraft.entity.player.PlayerEntity;

public class TubaHandler extends InstrumentHandler {
	private int basePitch = -1, dynamic = 77;

	public TubaHandler() {
		super(new int[] { GLFW.GLFW_KEY_8, GLFW.GLFW_KEY_9, GLFW.GLFW_KEY_0, GLFW.GLFW_KEY_MINUS, GLFW.GLFW_KEY_V,
				GLFW.GLFW_KEY_B, GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M, GLFW.GLFW_KEY_G, GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_J,
				GLFW.GLFW_KEY_K, GLFW.GLFW_KEY_Y, GLFW.GLFW_KEY_U, GLFW.GLFW_KEY_I, GLFW.GLFW_KEY_O, GLFW.GLFW_KEY_P,
				GLFW.GLFW_KEY_LEFT_BRACKET, GLFW.GLFW_KEY_RIGHT_BRACKET, GLFW.GLFW_KEY_BACKSLASH });
	}

	@Override
	public boolean isReady(PlayerEntity player) {
		boolean flag = player.getHeldItemMainhand().getItem() == ItemLoader.tuba.get()
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
		sendMessage(ShortMessage.PROGRAM_CHANGE, 0, 56, 0);
	}

	@Override
	protected void onStop() {
		if (basePitch >= 0) {
			sendMessage(ShortMessage.NOTE_OFF, 0,
					calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2), isPressed(3)), 0);
		}
	}

	@Override
	public void onStateChange(int index, boolean isPress) {
		if (index <= 3) {
			if (basePitch >= 0) {
				sendMessage(ShortMessage.NOTE_OFF, 0,
						calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2), isPressed(3)), 0);
				sendMessage(ShortMessage.NOTE_ON, 0,
						calcPitch(basePitch, index == 0 ? isPress : isPressed(0), index == 1 ? isPress : isPressed(1),
								index == 2 ? isPress : isPressed(2), index == 3 ? isPress : isPressed(3)),
						dynamic);
			}
			return;
		}
		if (index >= 12) {
			int d = (index - 11) * 10 + 47;
			if (d != dynamic) {
				dynamic = d;
				if (basePitch >= 0) {
					sendMessage(ShortMessage.NOTE_OFF, 0,
							calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2), isPressed(3)), 0);
					sendMessage(ShortMessage.NOTE_ON, 0,
							calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2), isPressed(3)), dynamic);
				}
			}
			return;
		}
		int pitch = 0;
		switch (index) {
		case 4:
			pitch = 39;
			break;
		case 5:
			pitch = 46;
			break;
		case 6:
			pitch = 53;
			break;
		case 7:
			pitch = 58;
			break;
		case 8:
			pitch = 62;
			break;
		case 9:
			pitch = 65;
			break;
		case 10:
			pitch = 70;
			break;
		case 11:
			pitch = 74;
			break;
		}
		if (isPress) {
			if (basePitch != pitch) {
				if (basePitch >= 0) {
					sendMessage(ShortMessage.NOTE_OFF, 0,
							calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2), isPressed(3)), 0);
				}
				sendMessage(ShortMessage.NOTE_ON, 0,
						calcPitch(pitch, isPressed(0), isPressed(1), isPressed(2), isPressed(3)), dynamic);
				basePitch = pitch;
			}
		} else if (basePitch == pitch) {
			sendMessage(ShortMessage.NOTE_OFF, 0,
					calcPitch(basePitch, isPressed(0), isPressed(1), isPressed(2), isPressed(3)), 0);
			basePitch = -1;
		}
	}

	private int calcPitch(int base, boolean button1, boolean button2, boolean button3, boolean button4) {
		if (button1) {
			base -= 2;
		}
		if (button2) {
			base--;
		}
		if (button3) {
			base -= 3;
		}
		if (button4) {
			base -= 5;
		}
		return base;
	}
}
