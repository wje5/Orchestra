package com.zonesoft.orchestra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.KeyboardListener;

@Mixin(KeyboardListener.class)
public class MixinKeyboardListener {
	@Inject(method = "onKeyEvent", at = @At("HEAD"), cancellable = true)
	public void injectKeyEvent(long windowPointer, int key, int scanCode, int action, int modifiers,
			CallbackInfo info) {
		System.out.println(windowPointer + "|" + key + "|" + scanCode + "|" + action + "|" + modifiers + "|");
		info.cancel();
	}
}
