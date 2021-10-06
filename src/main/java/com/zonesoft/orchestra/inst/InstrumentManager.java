package com.zonesoft.orchestra.inst;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.zonesoft.orchestra.ClientMidiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class InstrumentManager {
	private static List<InstrumentHandler> handlers = new ArrayList<InstrumentHandler>();

	public static void registerHandlers() {
		register(new TrumpetHandler());
		register(new TrumboneHandler());
		register(new TubaHandler());
		register(new FrenchHornHandler());
		register(new FluteHandler());
		register(new PiccoloHandler());
		register(new OboeHandler());
		register(new BassoonHandler());
		register(new ClarinetHandler());
		register(new SopranoSaxphoneHandler());
		register(new AltoSaxphoneHandler());
		register(new BaritoneSaxphoneHandler());
		register(new SnareDrumHandler());
		register(new BassDrumHandler());
		register(new CymbalHandler());
		register(new GlockenspielHandler());
	}

	public static void register(InstrumentHandler handler) {
		handlers.add(handler);
	}

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
		for (InstrumentHandler i : handlers) {
			if (i.isReady(player)) {
				return i;
			}
		}
		return null;
	}
}
