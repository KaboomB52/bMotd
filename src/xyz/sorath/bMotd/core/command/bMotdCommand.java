package xyz.sorath.bMotd.core.command;

import xyz.sorath.bMotd.bungee.bPlugin;
import xyz.sorath.bMotd.core.*;
import xyz.sorath.bMotd.core.util.*;
import java.util.*;

public abstract class bMotdCommand
{
    protected final bPlugin plugin;
    protected final Settings settings;
    private final String name;
    
    protected bMotdCommand(final bPlugin plugin, final Settings settings, final String name) {
        this.plugin = plugin;
        this.settings = settings;
        this.name = name;
    }
    
    public void execute(final SenderInfo sender, final String[] args) {
        if (this.checkPermission(sender, "command")) {
            return;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (this.checkPermission(sender, "reload")) {
                    return;
                }
                this.settings.reloadConfig();
                this.settings.reloadServerIcon();
                sender.sendMessage(this.plugin.getPrefix() + "§aReloaded config and the motd icon");
            }
            else if (args[0].equalsIgnoreCase("list")) {
                if (this.checkPermission(sender, "list")) {
                    return;
                }
                sender.sendMessage(this.plugin.getPrefix() + "§7List of your motds:");
                for (int i = 0; i < this.settings.getMotds().size(); ++i) {
                    sender.sendMessage("§b" + (i + 1) + "§8§m---------");
                    for (final String motd : this.settings.getColoredString(this.settings.getMotds().get(i)).split("%NEWLINE%")) {
                        sender.sendMessage(motd);
                    }
                }
                sender.sendMessage("§8§m----------");
            }
            else {
                this.sendUsage(sender);
            }
        }
        else if (args.length > 3 && args[0].equalsIgnoreCase("set")) {
            if (this.checkPermission(sender, "setmotd")) {
                return;
            }
            if (!this.isNumeric(args[1])) {
                sender.sendMessage(this.plugin.getPrefix() + "§cThe first argument has to be the motd index!");
                return;
            }
            final int index = Integer.parseInt(args[1]);
            if (index < 1 || index > this.settings.getMotds().size() + 1) {
                sender.sendMessage(this.plugin.getPrefix() + "§cYou currently have " + this.settings.getMotds().size() + " motds, so you have to pick a number between 1 and " + (this.settings.getMotds().size() + 1));
                return;
            }
            if (!this.isNumeric(args[2])) {
                sender.sendMessage(this.plugin.getPrefix() + "§cThe second argument has to be the line number (1 or 2)!");
                return;
            }
            final int line = Integer.parseInt(args[2]);
            if (line != 1 && line != 2) {
                sender.sendMessage(this.plugin.getPrefix() + "§cThe second argument has to be the line number (1 or 2)!");
                return;
            }
            final String message = String.join(" ", (CharSequence[])Arrays.copyOfRange(args, 3, args.length));
            final String oldMessage = (index > this.settings.getMotds().size()) ? "" : this.settings.getMotds().get(index - 1);
            String newMessage;
            if (line == 1) {
                newMessage = (oldMessage.contains("%NEWLINE%") ? (message + "%NEWLINE%" + oldMessage.split("%NEWLINE%", 2)[1]) : message);
            }
            else {
                newMessage = (oldMessage.contains("%NEWLINE%") ? (oldMessage.split("%NEWLINE%", 2)[0] + "%NEWLINE%" + message) : (oldMessage + "%NEWLINE%" + message));
            }
            if (index > this.settings.getMotds().size()) {
                this.settings.getMotds().add(newMessage);
            }
            else {
                this.settings.getMotds().set(index - 1, newMessage);
            }
            this.settings.setToConfig("motds", this.settings.getMotds());
            this.settings.saveConfig();
            sender.sendMessage(this.plugin.getPrefix() + "§aSet line " + line + " of the " + index + ". motd to §f" + this.settings.getColoredString(message));
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            if (this.checkPermission(sender, "setmotd")) {
                return;
            }
            if (!this.isNumeric(args[1])) {
                sender.sendMessage(this.plugin.getPrefix() + "§cThe argument has to be a number!");
                return;
            }
            if (this.settings.getMotds().size() <= 1) {
                sender.sendMessage(this.plugin.getPrefix() + "§cYou can only remove a motd if there are more than 1!");
                return;
            }
            final int index = Integer.parseInt(args[1]);
            if (index < 1 || index > this.settings.getMotds().size()) {
                sender.sendMessage(this.plugin.getPrefix() + "§cYou currently have " + this.settings.getMotds().size() + " motds, so you have to pick a number between 1 and " + this.settings.getMotds().size());
                return;
            }
            this.settings.getMotds().remove(index - 1);
            this.settings.setToConfig("motds", this.settings.getMotds());
            this.settings.saveConfig();
            sender.sendMessage(this.plugin.getPrefix() + "§7Removed §emotd " + index);
        }
        else {
            this.sendUsage(sender);
        }
    }
    
    private void sendUsage(final SenderInfo sender) {
        sender.sendMessage("");
        sender.sendMessage("§7§m-------------------------------------------------");
        sender.sendMessage("§6§lbMotd §7Commands");
        sender.sendMessage("§7§m-------------------------------------------------");
        if (sender.hasPermission("bMotd.reload")) {
            sender.sendMessage("§6/motd reload §7Reloads the plugin");
        }
        if (sender.hasPermission("bMotd.setmotd")) {
            sender.sendMessage("§6/motd set <index> <1/2> <motd> §7Sets the motd");
            sender.sendMessage("§6/motd remove <index> §7Deletes a motd");
        }
        if (sender.hasPermission("bMotd.list")) {
            sender.sendMessage("§6/motd list §7Lists all motds");
        }
        sender.sendMessage("§7§m-------------------------------------------------");
        sender.sendMessage("");
    }
    
    private boolean checkPermission(final SenderInfo sender, final String permission) {
        if (!sender.hasPermission("bMotd." + permission)) {
            sender.sendMessage(this.settings.getNoPermMessage());
            return true;
        }
        return false;
    }
    
    private boolean isNumeric(final String string) {
        try {
            Integer.parseInt(string);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    protected abstract void checkForUpdate(final SenderInfo p0);
}
