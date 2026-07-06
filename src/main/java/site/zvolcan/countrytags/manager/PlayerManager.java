package site.zvolcan.countrytags.manager;

import site.zvolcan.countrytags.data.PlayerData;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final Map<UUID, PlayerData> players = new ConcurrentHashMap<>();

    public PlayerData addPlayer(UUID uuid) {
        return players.computeIfAbsent(uuid, PlayerData::new);
    }

    public PlayerData removePlayer(UUID uuid) {
        return players.remove(uuid);
    }

    public Optional<PlayerData> getPlayer(UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }

    public Collection<PlayerData> getPlayers() {
        return players.values();
    }

}
