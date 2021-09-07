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
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		if (player == null) {
			return false;
		}
		if (trumpet.isReady(player)) {
			return trumpet.onKeyboardInput(key, action == GLFW.GLFW_RELEASE ? false : true);
		} else if (trumbone.isReady(player)) {
			return trumbone.onKeyboardInput(key, action == GLFW.GLFW_RELEASE ? false : true);
		} else if (tuba.isReady(player)) {
			return tuba.onKeyboardInput(key, action == GLFW.GLFW_RELEASE ? false : true);
		}
		if (action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_1) {
			ClientMidiHandler.reset();
			return true;
		}
		return false;
	}
}
