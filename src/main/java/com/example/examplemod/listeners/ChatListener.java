package com.example.examplemod.listeners;

import com.example.examplemod.ModBase;
import com.example.examplemod.TimeStampr;
import com.example.examplemod.utils.ColorCode;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatListener extends ModBase {

    private final TimeStampr main;

    public ChatListener () {
        main = TimeStampr.getInstance();
    }

    @SubscribeEvent
    public void chatmessage(final ClientChatReceivedEvent event) {
        if (main.getEnabled()) {
            final String colorString = ColorCode.valueOf(main.getColor()).toString();
            ChatComponentText message = new ChatComponentText(colorString + ClientThread.getTime(main));
            message.appendSibling(event.message);
        }
    }
}
