package com.zonesoft.orchestra.inst;

import com.zonesoft.orchestra.ClientMidiHandler;
import com.zonesoft.orchestra.network.MessageMidiToServer;
import com.zonesoft.orchestra.network.NetworkHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class InstrumentHandler {
	private int[] keys;
	private boolean[] states;
	private boolean running;

	public InstrumentHandler(int[] defaultKeys) {
		keys = defaultKeys;
		states = new boolean[keys.length];
	}

	public boolean isReady(PlayerEntity player) {
		return false;
	}

	public boolean onKeyboardInput(int key, boolean isPress) {
		for (int i = 0; i < keys.length; i++) {
			if (key == keys[i]) {
				if (states[i] != isPress) {
					onStateChange(i, isPress);
					states[i] = isPress;
				}
				return true;
			}
		}
		return false;
	}

	protected void onStateChange(int index, boolean isPress) {

	}

	protected boolean isPressed(int index) {
		return states[index];
	}

	protected void onStart() {

	}

	protected void onStop() {

	}

	public void start() {
		if (!running) {
			running = true;
			onStart();
		}
	}

	public void stop() {
		if (running) {
			running = false;
			onStop();
		}
	}

	public boolean isRunning() {
		return running;
	}

	public int getDynamic() {
		return -1;
	}

	protected void sendMessage(int command, int channel, int pitch, int keystroke) {
		Minecraft mc = Minecraft.getInstance();
		ClientMidiHandler.onMessage(mc.player.getUniqueID(), command, channel, pitch, keystroke);
		NetworkHandler.instance.sendToServer(new MessageMidiToServer(command, channel, pitch, keystroke));
	}
}
