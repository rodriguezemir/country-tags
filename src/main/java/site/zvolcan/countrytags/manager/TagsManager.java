package site.zvolcan.countrytags.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContents;
import org.bukkit.entity.Player;
import site.zvolcan.countrytags.data.PlayerData;

import java.util.Optional;
import java.util.UUID;

public class TagsManager {

    private final PlayerManager playerManager;

    public TagsManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void setTag(UUID uuid, String tag) {
        playerManager.getPlayer(uuid).ifPresent(playerData -> playerData.setTag(tag));
    }

    public Optional<String> getTag(UUID uuid) {
        return playerManager.getPlayer(uuid).map(PlayerData::getTag);
    }

    public boolean hasTag(UUID uuid) {
        return playerManager.getPlayer(uuid).map(PlayerData::hasTag).orElse(false);
    }

    public void removeTag(UUID uuid) {
        playerManager.getPlayer(uuid).ifPresent(playerData -> playerData.setTag(null));
    }

    public Component getPlayerHead(Player player) {
        return Component.object(
                ObjectContents.playerHead(player.getName())
        );
    }

}
