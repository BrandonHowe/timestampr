package com.example.examplemod;

import net.minecraft.client.Minecraft;

public abstract class ModBase
{
	protected static final Minecraft mc = Minecraft.getMinecraft();
	
	//We can't move the static variable Enabled to this base mod because then if one mod sets it to false
	//then ALL mods will be set to false
}