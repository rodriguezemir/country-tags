package site.zvolcan.countrytags;

import org.bukkit.plugin.java.JavaPlugin;
import site.zvolcan.countrytags.command.CountryTagCommand;
import site.zvolcan.countrytags.listener.PlayerConnectListener;
import site.zvolcan.countrytags.manager.ConfigManager;
import site.zvolcan.countrytags.manager.CountryManager;
import site.zvolcan.countrytags.manager.PlaceholdersManager;
import site.zvolcan.countrytags.manager.PlayerManager;
import site.zvolcan.countrytags.manager.TagsManager;

public class Main extends JavaPlugin {

    private ConfigManager configManager;
    private PlayerManager playerManager;
    private TagsManager tagsManager;
    private CountryManager countryManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        playerManager = new PlayerManager();
        tagsManager = new TagsManager(playerManager);
        countryManager = new CountryManager(playerManager, configManager);
        getServer().getPluginManager().registerEvents(new PlayerConnectListener(playerManager, countryManager), this);
        getCommand("countrytag").setExecutor(new CountryTagCommand(countryManager));

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholdersManager(this, playerManager, configManager).register();
        }
    }

    @Override
    public void onDisable() {
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public TagsManager getTagsManager() {
        return tagsManager;
    }

    public CountryManager getCountryManager() {
        return countryManager;
    }

}
