package com.zonesoft.orchestra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.zonesoft.orchestra.inst.InstrumentManager;

import net.minecraft.client.KeyboardListener;

@Mixin(KeyboardListener.class)
public class MixinKeyboardListener {
	@Inject(method = "onKeyEvent", at = @At("HEAD"), cancellable = true)
	public void injectKeyEvent(long windowPointer, int key, int scanCode, int action, int modifiers,
			CallbackInfo info) {
		if (InstrumentManager.onKeyboardInput(key, action)) {
			info.cancel();
		}
	}
}
