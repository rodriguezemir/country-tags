package site.zvolcan.countrytags.data;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private String tag;
    private Country country;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean hasTag() {
        return tag != null;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean hasCountry() {
        return country != null;
    }

}
