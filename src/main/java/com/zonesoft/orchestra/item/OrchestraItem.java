package com.zonesoft.orchestra.item;

import net.minecraft.item.Item;

public class OrchestraItem extends Item {
	public OrchestraItem(Properties prop) {
		super(prop.group(OrchestraTab.tab).maxStackSize(1));
	}
}
