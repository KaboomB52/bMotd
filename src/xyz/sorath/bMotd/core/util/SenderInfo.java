package xyz.sorath.bMotd.core.util;

import java.util.*;

public abstract class SenderInfo
{
    public abstract UUID getUuid();
    
    public abstract String getName();
    
    public abstract boolean hasPermission(final String p0);
    
    public abstract void sendMessage(final String p0);
}
