package xyz.elevated.frequency.data.type;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import xyz.elevated.frequency.data.PlayerData;

public final class PlayerDataManager {
  private final Map<UUID, PlayerData> playerDataMap = Maps.newConcurrentMap();

  public PlayerData getData(Player player) {
    return playerDataMap.computeIfAbsent(player.getUniqueId(), uuid -> new PlayerData(player));
  }

  public PlayerData remove(Player player) {
    UUID uuid = player.getUniqueId();

    return playerDataMap.remove(uuid);
  }

  public Collection<PlayerData> getEntries() {
    return playerDataMap.values();
  }
}
