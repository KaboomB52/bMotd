package xyz.sorath.bMotd.bungee;

import xyz.sorath.bMotd.core.*;
import xyz.sorath.bMotd.core.listener.*;
import xyz.sorath.bMotd.bungee.listener.*;
import java.nio.file.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.*;
import java.nio.charset.*;
import com.google.common.collect.*;
import net.md_5.bungee.api.*;
import java.io.*;
import java.util.*;

public final class SettingsBungee extends Settings
{
    private final bBase plugin;
    private final IPingListener pingListener;
    private Configuration config;
    
    SettingsBungee(final bBase plugin) {
        this.plugin = plugin;
        final PluginManager pm = plugin.getProxy().getPluginManager();
        final ProxyPingListener listener = new ProxyPingListener(this);
        pm.registerListener((Plugin)plugin, (Listener)listener);
        this.pingListener = listener;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        final File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                final InputStream in = plugin.getResourceAsStream("config.yml");
                try {
                    Files.copy(in, file.toPath(), new CopyOption[0]);
                    if (in != null) {
                        in.close();
                    }
                }
                catch (Throwable t) {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Throwable t2) {
                            t.addSuppressed(t2);
                        }
                    }
                    throw t;
                }
            }
            catch (IOException e) {
                throw new RuntimeException("Unable to create config.yml!", e);
            }
        }
        this.reloadConfig();
        this.reloadServerIcon();
    }
    
    @Override
    public void reloadConfig() {
        final File file = new File(this.plugin.getDataFolder(), "config.yml");
        try {
            this.config = YamlConfiguration.getProvider((Class)YamlConfiguration.class).load((Reader)new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load config.yml!", e);
        }
        this.loadSettings();
    }
    
    @Override
    public void updateConfig() {
        if (!this.config.contains("motd")) {
            return;
        }
        this.config.set("motds", (Object)Lists.newArrayList((Object[])new String[] { this.config.getString("motd") }));
        this.config.set("motd", (Object)null);
        this.saveConfig();
    }
    
    @Override
    public String getColoredString(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    @Override
    public void saveConfig() {
        final File file = new File(this.plugin.getDataFolder(), "config.yml");
        try {
            YamlConfiguration.getProvider((Class)YamlConfiguration.class).save(this.config, (Writer)new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to save config.yml!", e);
        }
    }
    
    @Override
    public void setToConfig(final String path, final Object var) {
        this.config.set(path, var);
    }
    
    @Override
    public String getConfigString(final String path) {
        final String s = this.config.getString(path);
        if (s == null) {
            this.plugin.getLogger().warning("The config is missing the following string: " + path);
            return "null";
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    @Override
    public boolean getConfigBoolean(final String path, final boolean def) {
        return this.config.getBoolean(path, def);
    }
    
    @Override
    public List<String> getConfigList(final String path) {
        return (List<String>)this.config.getStringList(path);
    }
    
    @Override
    public boolean reloadServerIcon() {
        return this.pingListener.loadIcon();
    }
}
