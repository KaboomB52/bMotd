package xyz.sorath.bMotd.core;

import xyz.sorath.bMotd.api.*;
import java.util.*;

public abstract class Settings implements ISettings
{
    private static final Random RANDOM;
    private boolean changePlayerCount;
    private boolean showPlayerCount;
    private boolean updateChecks;
    private List<String> motds;
    private String playerCountMessage;
    private String playerCountHoverMessage;
    private String noPermMessage;
    
    protected void loadSettings() {
        this.updateConfig();
        this.changePlayerCount = this.getConfigBoolean("custom-playercountmessage");
        this.showPlayerCount = this.getConfigBoolean("show-playercount");
        this.motds = this.getConfigList("motds");
        this.noPermMessage = this.getConfigString("no-permission-message");
        this.playerCountMessage = this.getConfigString("playercountmessage");
        this.playerCountHoverMessage = this.getConfigString("playercounthovermessage");
        this.updateChecks = this.getConfigBoolean("update-checks", true);
    }
    
    public abstract void updateConfig();
    
    public List<String> getMotds() {
        return this.motds;
    }
    
    @Override
    public String getMotd() {
        if (this.motds.isEmpty()) {
            return "";
        }
        final String s = (this.motds.size() > 1) ? this.motds.get(Settings.RANDOM.nextInt(this.motds.size())) : this.motds.get(0);
        return this.getColoredString(s).replace("%NEWLINE%", "\n");
    }
    
    @Override
    public String getPlayerCountMessage() {
        return this.playerCountMessage;
    }
    
    @Override
    public String getPlayerCountHoverMessage() {
        return this.playerCountHoverMessage;
    }
    
    @Override
    public boolean hasCustomPlayerCount() {
        return this.changePlayerCount;
    }
    
    @Override
    public boolean showPlayerCount() {
        return this.showPlayerCount;
    }
    
    @Override
    public boolean updateChecksEnabled() {
        return this.updateChecks;
    }
    
    public abstract String getColoredString(final String p0);
    
    public abstract void saveConfig();
    
    public abstract void setToConfig(final String p0, final Object p1);
    
    public abstract String getConfigString(final String p0);
    
    public abstract boolean getConfigBoolean(final String p0, final boolean p1);
    
    public boolean getConfigBoolean(final String path) {
        return this.getConfigBoolean(path, false);
    }
    
    public abstract List<String> getConfigList(final String p0);
    
    public String getNoPermMessage() {
        return this.noPermMessage;
    }
    
    static {
        RANDOM = new Random();
    }
}
