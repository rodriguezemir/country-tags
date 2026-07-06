package site.zvolcan.countrytags.manager;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.zvolcan.countrytags.data.Country;
import site.zvolcan.countrytags.data.PlayerData;

public class PlaceholdersManager extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private final PlayerManager playerManager;
    private final ConfigManager configManager;

    public PlaceholdersManager(JavaPlugin plugin, PlayerManager playerManager, ConfigManager configManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.configManager = configManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "countrytags";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getPluginMeta().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null || !canShowTag(player)) {
            return "";
        }

        PlayerData playerData = playerManager.getPlayer(player.getUniqueId()).orElse(null);

        return switch (params.toLowerCase()) {
            case "tag" -> resolveTag(playerData);
            case "head" -> resolveHead(player, playerData);
            default -> null;
        };
    }

    private boolean canShowTag(OfflinePlayer player) {
        if (!configManager.isPermissionEnabled()) {
            return true;
        }
        Player online = player.getPlayer();
        return online != null && online.hasPermission(configManager.getPermissionNode());
    }

    private String resolveTag(PlayerData playerData) {
        if (playerData == null) {
            return "";
        }
        if (playerData.hasTag()) {
            return playerData.getTag();
        }
        if (playerData.hasCountry()) {
            return playerData.getCountry().getTag();
        }
        return "";
    }

    private String resolveHead(OfflinePlayer player, PlayerData playerData) {
        if (playerData != null && playerData.hasCountry()) {
            Country country = playerData.getCountry();
            if (!country.getSkinUrl().isEmpty()) {
                return "<head:" + country.getSkinUrl() + ">";
            }
        }

        String name = player.getName();
        return name != null ? "<head:" + name + ">" : "";
    }

}
