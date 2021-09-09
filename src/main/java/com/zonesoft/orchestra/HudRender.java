package com.zonesoft.orchestra;

import com.zonesoft.orchestra.inst.InstrumentHandler;
import com.zonesoft.orchestra.inst.InstrumentManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class HudRender {
	public static ResourceLocation LOCATION = new ResourceLocation("orchestra:textures/gui/hud.png");

	@SubscribeEvent
	public static void onRenderScreen(RenderGameOverlayEvent.Post event) {
		if (event.getType() == ElementType.HOTBAR) {
			InstrumentHandler handler = InstrumentManager.getHandler();
			if (handler != null) {
				int dynamic = handler.getDynamic();
				if (dynamic >= 0) {
					Minecraft mc = Minecraft.getInstance();
					mc.getTextureManager().bindTexture(LOCATION);
					int x = event.getWindow().getScaledWidth() - 30;
					int y = event.getWindow().getScaledHeight() / 2 - 56;
					IngameGui.blit(event.getMatrixStack(), x, y, 0, 0, 30, 112, 256, 256);
					IngameGui.blit(event.getMatrixStack(), x + 5, y + 92 - (int) (74 * (dynamic / 7F)), 30, 0, 11, 1,
							256, 256);
				}
			}
		}
	}
}
