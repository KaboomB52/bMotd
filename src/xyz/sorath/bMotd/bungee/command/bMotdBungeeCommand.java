package xyz.sorath.bMotd.bungee.command;

import xyz.sorath.bMotd.core.command.*;
import xyz.sorath.bMotd.bungee.*;
import xyz.sorath.bMotd.core.*;
import xyz.sorath.bMotd.core.util.*;
import net.md_5.bungee.api.chat.*;
import xyz.sorath.bMotd.bungee.util.*;

public final class bMotdBungeeCommand extends bMotdCommand
{
    public bMotdBungeeCommand(final bPlugin plugin, final SettingsBungee settings) {
        super(plugin, settings, "bMotdBungee");
    }

    @Override
    protected void checkForUpdate(SenderInfo p0) {

    }
}
