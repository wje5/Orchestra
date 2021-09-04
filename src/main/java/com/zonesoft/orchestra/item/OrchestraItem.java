package com.zonesoft.orchestra.item;

import net.minecraft.item.Item;

public class OrchestraItem extends Item {
	public OrchestraItem(Properties prop) {
		super(prop.tab(OrchestraTab.tab).stacksTo(1));
	}
}
