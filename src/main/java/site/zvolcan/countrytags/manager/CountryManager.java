package site.zvolcan.countrytags.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import site.zvolcan.countrytags.data.Country;
import site.zvolcan.countrytags.data.PlayerData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CountryManager {

    private final PlayerManager playerManager;
    private final ConfigManager configManager;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public CountryManager(PlayerManager playerManager, ConfigManager configManager) {
        this.playerManager = playerManager;
        this.configManager = configManager;
    }

    public CompletableFuture<Optional<Country>> fetchCountry(String ip) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configManager.getApiUrl().replace("{ip}", ip)))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(this::parseCountry)
                .exceptionally(throwable -> Optional.empty());
    }

    public CompletableFuture<Optional<Country>> loadCountry(UUID uuid, String ip) {
        return fetchCountry(ip).thenApply(country -> {
            country.ifPresent(value -> setCountry(uuid, value));
            return country;
        });
    }

    public void setCountry(UUID uuid, Country country) {
        playerManager.getPlayer(uuid).ifPresent(playerData -> playerData.setCountry(country));
    }

    public Optional<Country> getCountry(UUID uuid) {
        return playerManager.getPlayer(uuid).map(PlayerData::getCountry);
    }

    public boolean hasCountry(UUID uuid) {
        return playerManager.getPlayer(uuid).map(PlayerData::hasCountry).orElse(false);
    }

    private Optional<Country> parseCountry(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            return Optional.empty();
        }

        JsonElement root = JsonParser.parseString(response.body());
        if (!root.isJsonObject()) {
            return Optional.empty();
        }

        JsonObject json = root.getAsJsonObject();
        JsonElement countryCode = json.get("countryCode");
        if (countryCode == null || countryCode.isJsonNull()) {
            return Optional.empty();
        }

        return Country.fromCode(countryCode.getAsString());
    }

}
