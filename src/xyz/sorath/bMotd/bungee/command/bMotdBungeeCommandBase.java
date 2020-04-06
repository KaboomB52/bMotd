package xyz.sorath.bMotd.bungee.command;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import xyz.sorath.bMotd.bungee.util.*;
import xyz.sorath.bMotd.core.util.*;

public final class bMotdBungeeCommandBase extends Command
{
    private final bMotdBungeeCommand command;
    
    public bMotdBungeeCommandBase(final bMotdBungeeCommand command) {
        super("bMotd", "", new String[] { "motd", "bMotdbungee" });
        this.command = command;
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        this.command.execute(new ProxiedSenderInfo(sender), args);
    }
}
