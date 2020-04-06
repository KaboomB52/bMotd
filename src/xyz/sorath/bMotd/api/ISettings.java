package xyz.sorath.bMotd.api;

public interface ISettings
{
    String getMotd();
    
    void reloadConfig();
    
    String getPlayerCountMessage();
    
    String getPlayerCountHoverMessage();
    
    boolean reloadServerIcon();
    
    boolean hasCustomPlayerCount();
    
    boolean showPlayerCount();
    
    boolean updateChecksEnabled();
}
