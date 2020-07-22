package com.example.examplemod;

import com.example.examplemod.commands.TimeStamprCommand;
import com.example.examplemod.config.Config;
import com.example.examplemod.listeners.ChatListener;
import com.mojang.authlib.GameProfile;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.HashMap;

@Mod(modid = TimeStampr.MODID, version = TimeStampr.VERSION)
public class TimeStampr
{
	protected static final Minecraft mc = Minecraft.getMinecraft();
    public static final String MODID = "timestampr";
    public static final String NAME = "TimeStampr";
    public static final String VERSION = "1.0";

    private Config config;
    private File configFile;

    public static boolean timestamprEnabled = true;

    @Getter
    private static TimeStampr instance;

    public HashMap<String, String> getSettings() {
        return config.getSettings();
    }

    public String getColor() {
        return config.getColor();
    }

    public void setColor(String val) {
        config.setColor(val);
        saveConfig();
    }

    public boolean isEnabled() {
        return config.isEnabled();
    }

    public void setEnabled(boolean val) {
        config.setEnabled(val);
        saveConfig();
    }

    public boolean isSeconds () {
        return config.isSeconds();
    }

    public void setSeconds(boolean val) {
        config.setSeconds(val);
        saveConfig();
    }

    public boolean isMilliseconds () {
        return config.isMilliseconds();
    }

    public void setMilliseconds(boolean val) {
        config.setMilliseconds(val);
        saveConfig();
    }

    public boolean is24Hour () {
        return config.isHour24();
    }

    public void set24Hour(boolean val) {
        config.setHour24(val);
        saveConfig();
    }

    public void reloadConfig() {
        config = new Config(configFile);
    }

    public void saveConfig() {
        config.saveConfig();
    }

    public TimeStampr () {
        instance = this;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.configFile = event.getSuggestedConfigurationFile();
        reloadConfig();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        ClientCommandHandler.instance.registerCommand(new TimeStamprCommand());
    }

    public static void sendMessage(String text) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 1, new ChatComponentText(text));
        MinecraftForge.EVENT_BUS.post(event); // Let other mods pick up the new message
        if (!event.isCanceled()) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(event.message); // Just for logs
        }
    }

    public GameProfile getGameProfile() {
        return mc.getSession().getProfile();
    }
}
