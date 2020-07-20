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

    private TimeStampr main;

    public TimeStamprCommand() {
        this.main = TimeStampr.getInstance();
    }

    @Override
    public String getCommandName() {
        return "timestampr";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "§7§m------------§7[§b§l TimeStampr §7]§7§m------------" + "\n" +
                "§b● /timestampr §7- Open this help message" + "\n" +
                "§b● /timestampr prefix §7- Edit timestamp prefix" + "\n" +
                "§b● /timestampr separator §7- Edit timestamp separator" + "\n" +
                "§b● /timestampr suffix §7- Edit timestamp suffix" + "\n" +
                "§b● /timestampr color §7- Edit timestamp color" + "\n" +
                "§7§m------------------------------------------";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "prefix", "suffix", "separator", "status");
        }

        return null;
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) throws CommandException {
        if (args.length == 0) {
            TimeStampr.sendMessage("Hi, welcome to TimeStampr v" + TimeStampr.VERSION);
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
                final String[] colors = {"RED", "GRAY", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "LIGHT_PURPLE", "YELLOW", "WHITE", "MAGIC", "BOLD", "STRIKETHROUGH", "UNDERLINE", "ITALIC"};
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
