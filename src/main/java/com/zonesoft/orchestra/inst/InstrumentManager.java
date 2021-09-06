package com.zonesoft.orchestra.inst;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.ClientMidiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class InstrumentManager {
	public static TrumpetHandler trumpet = new TrumpetHandler();

	@SubscribeEvent
	public static void onKeyboardInput(KeyInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		if (player == null) {
			return;
		}
		int action = event.getAction();
		int key = event.getKey();
		if (trumpet.isReady(player)) {
			trumpet.onKeyboardInput(key, action == GLFW.GLFW_RELEASE ? false : true);
		}
		if (action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_1) {
			ClientMidiHandler.reset();
		}
	}
}
