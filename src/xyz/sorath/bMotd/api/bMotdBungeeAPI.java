package xyz.sorath.bMotd.api;

import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;

public final class bMotdBungeeAPI
{
    public static IbMotd getAPI() {
        final Plugin bMotd = ProxyServer.getInstance().getPluginManager().getPlugin("bMotd");
        if (bMotd == null) {
            ProxyServer.getInstance().getLogger().warning("Could not get instance of bMotd!");
            return null;
        }
        return ((IbMotdBase)bMotd).getApi();
    }
}
