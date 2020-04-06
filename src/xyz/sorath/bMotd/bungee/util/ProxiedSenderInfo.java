package xyz.sorath.bMotd.bungee.util;

import xyz.sorath.bMotd.core.util.*;
import net.md_5.bungee.api.*;
import java.util.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.chat.*;

public final class ProxiedSenderInfo extends SenderInfo
{
    private final CommandSender sender;
    
    public ProxiedSenderInfo(final CommandSender sender) {
        this.sender = sender;
    }
    
    @Override
    public UUID getUuid() {
        return (this.sender instanceof ProxiedPlayer) ? ((ProxiedPlayer)this.sender).getUniqueId() : null;
    }
    
    @Override
    public String getName() {
        return this.sender.getName();
    }
    
    @Override
    public boolean hasPermission(final String permission) {
        return this.sender.hasPermission(permission);
    }
    
    @Override
    public void sendMessage(final String message) {
        this.sender.sendMessage(message);
    }
    
    public void sendMessage(final TextComponent textComponent) {
        this.sender.sendMessage((BaseComponent)textComponent);
    }
}
