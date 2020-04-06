package xyz.sorath.bMotd.bungee;

import xyz.sorath.bMotd.core.*;
import xyz.sorath.bMotd.bungee.listener.*;
import xyz.sorath.bMotd.bungee.command.*;
import net.md_5.bungee.api.plugin.*;
import xyz.sorath.bMotd.api.*;
import java.io.*;

public final class bPlugin extends bMotdCore {
    private final bBase plugin;
    private final SettingsBungee settings;

    bPlugin(final bBase plugin) {
        super("&6&lbMotd", plugin.getDescription().getVersion());
        this.plugin = plugin;
        plugin.getLogger().info("Plugin by Sorath Development");
        this.settings = new SettingsBungee(plugin);
        final PluginManager pm = plugin.getProxy().getPluginManager();
        pm.registerListener((Plugin)plugin, (Listener)new PostLoginListener(this));
        final bMotdBungeeCommand command = new bMotdBungeeCommand(this, this.settings);
        pm.registerCommand((Plugin)plugin, (Command)new bMotdBungeeCommandBase(command));
    }

    @Deprecated
    public static IbMotd getAPI() {
        return bMotdBungeeAPI.getAPI();
    }

    @Override
    public ISettings getSettings() {
        return this.settings;
    }

    @Override
    public File getPluginFile() {
        return this.plugin.getPluginFile();
    }

    @Override
    public void async(final Runnable runnable) {
        this.plugin.getProxy().getScheduler().runAsync((Plugin)this.plugin, runnable);
    }
}
