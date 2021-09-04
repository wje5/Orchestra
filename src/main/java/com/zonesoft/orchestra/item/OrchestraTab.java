package com.zonesoft.orchestra.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class OrchestraTab extends ItemGroup {
	public static final ItemGroup tab = new OrchestraTab();

	public OrchestraTab() {
		super("orchestra");
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemLoader.trumpet.get());
	}
}
