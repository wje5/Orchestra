package com.zonesoft.orchestra.inst;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.ClientMidiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class InstrumentManager {
	public static TrumpetHandler trumpet = new TrumpetHandler();
	public static TrumboneHandler trumbone = new TrumboneHandler();
	public static TubaHandler tuba = new TubaHandler();

	public static boolean onKeyboardInput(int key, int action) {
		if (action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_1) {
			ClientMidiHandler.reset();
			return true;
		}
		InstrumentHandler handler = getHandler();
		if (handler != null) {
			return handler.onKeyboardInput(key, action == GLFW.GLFW_RELEASE ? false : true);
		}
		return false;
	}

	public static InstrumentHandler getHandler() {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		if (player == null) {
			return null;
		}
		if (trumpet.isReady(player)) {
			return trumpet;
		} else if (trumbone.isReady(player)) {
			return trumbone;
		} else if (tuba.isReady(player)) {
			return tuba;
		}
		return null;
	}
}
