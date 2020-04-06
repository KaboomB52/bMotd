package xyz.sorath.bMotd.core;

import xyz.sorath.bMotd.api.*;
import java.net.*;
import java.io.*;

public abstract class bMotdCore implements IbMotd
{
    protected final String version;
    private final String prefix;
    private String newestVersion;
    
    protected bMotdCore(final String prefix, final String version) {
        this.prefix = prefix;
        this.version = version;
    }
    
    public abstract File getPluginFile();
    
    public abstract void async(final Runnable p0);
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getNewestVersion() {
        return this.newestVersion;
    }
}
