package com.zonesoft.orchestra.inst;

import javax.sound.midi.ShortMessage;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.item.ItemLoader;

import net.minecraft.entity.player.PlayerEntity;

public class SnareDrumHandler extends InstrumentHandler {
	public SnareDrumHandler() {
		super(new int[] { GLFW.GLFW_KEY_V, GLFW.GLFW_KEY_B, GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M, GLFW.GLFW_KEY_G,
				GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_J, GLFW.GLFW_KEY_K });
	}

	@Override
	public boolean isReady(PlayerEntity player) {
		boolean flag = player.getHeldItemMainhand().getItem() == ItemLoader.snare_drum.get()
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
	public void onStateChange(int index, boolean isPress) {
		if (isPress) {
			sendMessage(ShortMessage.NOTE_ON, 10, 38, index * 10 + 57);
		}
	}
}
