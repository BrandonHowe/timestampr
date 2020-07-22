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
            "\u00a76 /timestampr <enable|disable> \u00a77- Enable or disable TimeStampr" + "\n" +
            "\u00a76 /timestampr prefix \u00a77- Edit timestamp prefix" + "\n" +
            "\u00a76 /timestampr separator \u00a77- Edit timestamp separator" + "\n" +
            "\u00a76 /timestampr suffix \u00a77- Edit timestamp suffix" + "\n" +
            "\u00a76 /timestampr color [color] \u00a77- Edit timestamp color" + "\n" +
            "\u00a76 /timestampr seconds <on|off> \u00a77- Enable or disable seconds" + "\n" +
            "\u00a76 /timestampr milliseconds <on|off> \u00a77- Enable or disable milliseconds" + "\n" +
            "\u00a76 /timestampr 24hour <on|off> \u00a77- Enable or disable 24 hour time" + "\n" +
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
            return getListOfStringsMatchingLastWord(args, "prefix", "suffix", "separator", "status", "color", "seconds", "milliseconds", "24hour", "enable", "disable");
        } else if (args[0].equals("color") && args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "red", "gray", "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "dark_gray", "blue", "green", "aqua", "light_purple", "yellow", "white");
        } else if (args.length == 2 && ArrayUtils.contains(new String[]{"seconds", "milliseconds", "24hour"}, args[0].toLowerCase())) {
            return getListOfStringsMatchingLastWord(args, "on", "off");
        }

        return null;
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) throws CommandException {
        if (args.length == 0) {
            TimeStampr.sendMessage(helpMessage);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                if (main.isEnabled()) {
                    TimeStampr.sendMessage("TimeStampr is already enabled!");
                } else {
                    main.setEnabled(true);
                    TimeStampr.sendMessage("Enabled TimeStampr!");
                }
            } else if (args[0].equalsIgnoreCase("disable")) {
                if (!main.isEnabled()) {
                    TimeStampr.sendMessage("TimeStampr is already disabled!");
                } else {
                    main.setEnabled(false);
                    TimeStampr.sendMessage("Disabled TimeStampr!");
                }
            }
        } else {
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
                }
            } else if (args[0].equalsIgnoreCase("color")) {
                final String capitalizedArg = args[1].toUpperCase();
                final String[] colors = {"RED", "GRAY", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "LIGHT_PURPLE", "YELLOW", "WHITE"};
                if (ArrayUtils.contains(colors, capitalizedArg)) {
                    main.setColor(capitalizedArg);
                    TimeStampr.sendMessage("Changed TimeStampr color to " + ColorCode.valueOf(capitalizedArg).toString() + args[1].toLowerCase());
                } else {
                    TimeStampr.sendMessage("That color is not valid!");
                }
            } else if (args[0].equalsIgnoreCase("seconds")) {
                final boolean args1Enable = args[1].equalsIgnoreCase("on");
                final boolean args1Disable = args[1].equalsIgnoreCase("off");
                if (!args1Enable && !args1Disable) {
                    TimeStampr.sendMessage("You have to specify on or off!");
                } else if (args1Enable) {
                    if (main.isSeconds()) {
                        TimeStampr.sendMessage("Seconds are already enabled!");
                    } else {
                        main.setSeconds(true);
                        TimeStampr.sendMessage("Successfully enabled seconds!");
                    }
                } else {
                    if (!main.isSeconds()) {
                        TimeStampr.sendMessage("Seconds are already disabled!");
                    } else {
                        main.setSeconds(false);
                        TimeStampr.sendMessage("Successfully disabled seconds!");
                    }
                }
            } else if (args[0].equalsIgnoreCase("milliseconds")) {
                final boolean argvs1Enable = args[1].equalsIgnoreCase("on");
                final boolean args1Disable = args[1].equalsIgnoreCase("off");
                if (!args1Enable && !args1Disable) {
                    TimeStampr.sendMessage("You have to specify on or off!");
                } else if (args1Enable) {
                    if (main.isMilliseconds()) {
                        TimeStampr.sendMessage("Milliseconds are already enabled!");
                    } else {
                        main.setMilliseconds(true);
                        TimeStampr.sendMessage("Successfully enabled milliseconds!");
                    }
                } else {
                    if (!main.isMilliseconds()) {
                        TimeStampr.sendMessage("Milliseconds are already disabled!");
                    } else {
                        main.setMilliseconds(false);
                        TimeStampr.sendMessage("Successfully disabled milliseconds!");
                    }
                }
            } else if (args[0].equalsIgnoreCase("24hour")) {
                final boolean args1Enable = args[1].equalsIgnoreCase("on");
                final boolean args1Disable = args[1].equalsIgnoreCase("off");
                if (!args1Enable && !args1Disable) {
                    TimeStampr.sendMessage("You have to specify on or off!");
                } else if (args1Enable) {
                    if (main.is24Hour()) {
                        TimeStampr.sendMessage("24 hour mode is already enabled!");
                    } else {
                        main.set24Hour(true);
                        TimeStampr.sendMessage("Successfully enabled 24 hour mode!");
                    }
                } else {
                    if (!main.is24Hour()) {
                        TimeStampr.sendMessage("24 hour mode is already disabled!");
                    } else {
                        main.set24Hour(false);
                        TimeStampr.sendMessage("Successfully disabled 24 hour mode!");
                    }
                }
            } else {
                TimeStampr.sendMessage("You need your new value to be in quotes!");
            }
        }
    }

    ;
}
