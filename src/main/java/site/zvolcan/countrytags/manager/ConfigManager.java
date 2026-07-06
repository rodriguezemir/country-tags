package site.zvolcan.countrytags.manager;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private static final String DEFAULT_API_URL = "https://free.freeipapi.com/api/json/{ip}";
    private static final String DEFAULT_PERMISSION_NODE = "countrytags.tag.show";

    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public void reload() {
        plugin.reloadConfig();
    }

    public String getApiUrl() {
        return plugin.getConfig().getString("api.url", DEFAULT_API_URL);
    }

    public boolean isPermissionEnabled() {
        return plugin.getConfig().getBoolean("permission.enabled", true);
    }

    public String getPermissionNode() {
        return plugin.getConfig().getString("permission.node", DEFAULT_PERMISSION_NODE);
    }

}
