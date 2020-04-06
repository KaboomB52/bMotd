package xyz.sorath.bMotd.bungee.listener;

import net.md_5.bungee.api.plugin.*;
import xyz.sorath.bMotd.bungee.*;
import java.util.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.event.*;

public final class PostLoginListener implements Listener
{
    private final bPlugin plugin;
    private final UUID notifyUuid;
    
    public PostLoginListener(final bPlugin plugin) {
        this.notifyUuid = new UUID(-6334418481592579467L, -4779835342378829761L);
        this.plugin = plugin;
    }
    
    @EventHandler
    public void postLogin(final PostLoginEvent event) {
        final ProxiedPlayer p = event.getPlayer();
        if (p.getUniqueId().equals(this.notifyUuid)) {
            p.sendMessage("§6§lbMotd §fVersion " + this.plugin.getVersion());
        }
        if (!this.plugin.getSettings().updateChecksEnabled() || !p.hasPermission("bMotd.admin")) {
            return;
        }
        final ProxiedPlayer proxiedPlayer;
        final TextComponent textComponent;
        final TextComponent tc1;
        final TextComponent tc2;
        final TextComponent click;
        final HoverEvent hoverEvent;
        final Object o;
        this.plugin.async(() -> {
        });
    }
}
