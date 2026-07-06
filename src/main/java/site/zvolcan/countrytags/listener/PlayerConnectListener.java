package site.zvolcan.countrytags.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import site.zvolcan.countrytags.manager.CountryManager;
import site.zvolcan.countrytags.manager.PlayerManager;

import java.net.InetSocketAddress;

public class PlayerConnectListener implements Listener {

    private final PlayerManager playerManager;
    private final CountryManager countryManager;

    public PlayerConnectListener(PlayerManager playerManager, CountryManager countryManager) {
        this.playerManager = playerManager;
        this.countryManager = countryManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerManager.addPlayer(event.getPlayer().getUniqueId());

        InetSocketAddress address = event.getPlayer().getAddress();
        if (address != null && address.getAddress() != null) {
            countryManager.loadCountry(event.getPlayer().getUniqueId(), address.getAddress().getHostAddress());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerManager.removePlayer(event.getPlayer().getUniqueId());
    }

}
