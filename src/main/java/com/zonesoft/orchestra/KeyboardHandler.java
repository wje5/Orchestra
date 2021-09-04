package com.zonesoft.orchestra;

import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class KeyboardHandler {
	@SubscribeEvent
	public static void onKeyboardInput(KeyInputEvent event) {
//		int action = event.getAction();
//		int pitch = 76;
//		if (action == GLFW.GLFW_PRESS) {
//			Minecraft mc = Minecraft.getInstance();
//			UUID uuid = mc.player.getUUID();
//			ClientMidiHandler.onMessage(uuid, ShortMessage.PROGRAM_CHANGE, 56, 0);
//			ClientMidiHandler.onMessage(uuid, ShortMessage.NOTE_ON, pitch, 65);
//		} else if (action == GLFW.GLFW_RELEASE) {
//			Minecraft mc = Minecraft.getInstance();
//			UUID uuid = mc.player.getUUID();
//			ClientMidiHandler.onMessage(uuid, ShortMessage.NOTE_OFF, pitch, 0);
//		}
	}
}
