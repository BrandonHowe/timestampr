package com.example.examplemod.commands;

import com.example.examplemod.TimeStampr;
import com.example.examplemod.utils.ColorCode;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

public class TimeStamprCommand extends CommandBase {

    private final TimeStampr main;

    private final String helpMessage = "\u00a77\u00a7m------------\u00a77[\u00a76\u00a7l TimeStampr v" + TimeStampr.VERSION + " \u00a77]\u00a77\u00a7m------------" + "\n" +
            "\u00a76 /timestampr \u00a77- Open this help message" + "\n" +
            "\u00a76 /timestampr prefix \u00a77- Edit timestamp prefix" + "\n" +
            "\u00a76 /timestampr separator \u00a77- Edit timestamp separator" + "\n" +
            "\u00a76 /timestampr suffix \u00a77- Edit timestamp suffix" + "\n" +
            "\u00a76 /timestampr color \u00a77- Edit timestamp color" + "\n" +
            "\u00a77\u00a7m------------------------------------------";

    public TimeStamprCommand() {
        this.main = TimeStampr.getInstance();
    }

    @Override
    public String getCommandName() {
        return "timestampr";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return helpMessage;
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "prefix", "suffix", "separator", "status", "color");
        } else if (args[0].equals("color") && args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "red", "gray", "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "dark_gray", "blue", "green", "aqua", "light_purple", "yellow", "white");
        }

        return null;
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) throws CommandException {
        if (args.length == 0) {
            TimeStampr.sendMessage(helpMessage);
        } else if (args.length >= 2) {
            String condensedArgs = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            if (condensedArgs.charAt(0) == '"' && condensedArgs.charAt(condensedArgs.length() - 1) == '"') {
                String sliceFirst = condensedArgs.substring(1);
                String slicedArg = sliceFirst.substring(0, sliceFirst.length() - 1);
                if (args[0].equalsIgnoreCase("prefix")) {
                    main.getSettings().replace("prefix", slicedArg);
                    main.saveConfig();
                    TimeStampr.sendMessage("Changed TimeStampr prefix to " + slicedArg);
                } else if (args[0].equalsIgnoreCase("suffix")) {
                    main.getSettings().replace("suffix", slicedArg);
                    main.saveConfig();
                    TimeStampr.sendMessage("Changed TimeStampr suffix to " + slicedArg);
                } else if (args[0].equalsIgnoreCase("separator")) {
                    main.getSettings().replace("separator", slicedArg);
                    main.saveConfig();
                    TimeStampr.sendMessage("Changed TimeStampr separator to " + slicedArg);
                } else if (args[0].equalsIgnoreCase("status")) {
                    if (args[1].equalsIgnoreCase("on")) {

                    } else if (args[1].equalsIgnoreCase("off")) {

                    } else {
                        TimeStampr.sendMessage("The status must be either on or off!");
                    }
                }
            } else if (args[0].equalsIgnoreCase("color")) {
                final String capitalizedArg = args[1].toUpperCase();
                final String[] colors = {"RED", "GRAY", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "LIGHT_PURPLE", "YELLOW", "WHITE"};
                if (ArrayUtils.contains(colors, capitalizedArg)) {
                    main.setColor(capitalizedArg);
                    main.saveConfig();
                    TimeStampr.sendMessage("Changed TimeStampr color to " + ColorCode.valueOf(capitalizedArg).toString() + args[1].toLowerCase());
                } else {
                    TimeStampr.sendMessage("That color is not valid!");
                }
            } else {
                TimeStampr.sendMessage("You need your new value to be in quotes!");
            }
        }
    }

    ;
}
