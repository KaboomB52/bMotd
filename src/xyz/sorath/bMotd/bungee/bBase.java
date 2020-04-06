package xyz.sorath.bMotd.bungee;

import net.md_5.bungee.api.plugin.*;
import xyz.sorath.bMotd.api.*;
import java.io.*;

public final class bBase extends Plugin implements IbMotdBase
{
    private IbMotd bMotd;
    
    public void onEnable() {
        this.bMotd = new bPlugin(this);
    }
    
    public IbMotd getApi() {
        return this.bMotd;
    }
    
    File getPluginFile() {
        return this.getFile();
    }
}
