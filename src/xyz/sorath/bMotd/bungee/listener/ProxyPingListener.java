package xyz.sorath.bMotd.bungee.listener;

import net.md_5.bungee.api.plugin.*;
import xyz.sorath.bMotd.core.listener.*;
import xyz.sorath.bMotd.core.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.event.*;
import javax.imageio.*;
import java.io.*;

public final class ProxyPingListener implements Listener, IPingListener
{
    private static final String PLAYERS_FORMAT = " §7%d§8/§7%d";
    private final Settings settings;
    private Favicon favicon;
    
    public ProxyPingListener(final Settings settings) {
        this.settings = settings;
    }
    
    @EventHandler(priority = 32)
    public void proxyPing(final ProxyPingEvent event) {
        final ServerPing ping = event.getResponse();
        if (this.settings.hasCustomPlayerCount()) {
            ping.getVersion().setProtocol(1);
            ping.getVersion().setName(this.settings.showPlayerCount() ? (this.settings.getPlayerCountMessage() + String.format(" §7%d§8/§7%d", ping.getPlayers().getOnline(), ping.getPlayers().getMax())) : this.settings.getPlayerCountMessage());
        }
        ping.getPlayers().setSample(new ServerPing.PlayerInfo[] { new ServerPing.PlayerInfo(this.settings.getPlayerCountHoverMessage().replace("%NEWLINE%", "\n"), "") });
        ping.setDescriptionComponent((BaseComponent)new TextComponent(TextComponent.fromLegacyText(this.settings.getMotd())));
        if (this.favicon != null) {
            ping.setFavicon(this.favicon);
        }
    }
    
    public boolean loadIcon() {
        try {
            final File file = new File("server-icon.png");
            if (!file.exists()) {
                return false;
            }
            this.favicon = Favicon.create(ImageIO.read(file));
        }
        catch (IOException | IllegalArgumentException ex2) {
            final Exception ex;
            return false;
        }
        return true;
    }
}
