package xyz.elevated.frequency.data;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.elevated.frequency.data.impl.*;
import xyz.elevated.frequency.exempt.ExemptManager;
import xyz.elevated.frequency.observable.Observable;
import xyz.elevated.frequency.update.PositionUpdate;
import xyz.elevated.frequency.update.RotationUpdate;
import xyz.elevated.frequency.util.EvictingList;
import xyz.elevated.frequency.util.EvictingMap;
import xyz.elevated.frequency.util.NmsUtil;
import xyz.elevated.frequency.util.Pair;

@Getter
@Setter
public final class PlayerData {
  private final Player bukkitPlayer;
  private final PlayerConnection connection;

  private final EvictingList<BoundingBox> boundingBoxes = new EvictingList<>(10);
  private final EvictingList<Location> locationsSent = new EvictingList<>(10);
  private final EvictingList<Pair<AxisAlignedBB, Integer>> targetLocations = new EvictingList<>(30);

  private final EvictingMap<Short, Long> transactionUpdates = new EvictingMap<>(20);
  private final EvictingMap<Integer, Long> keepAliveUpdates = new EvictingMap<>(20);

  private final Observable<Boolean> sprinting = new Observable<>(false);
  private final Observable<Boolean> cinematic = new Observable<>(false);
  private final Observable<Integer> joined = new Observable<>(0);
  private final Observable<Entity> target = new Observable<>(null);
  private final Observable<Long> keepAlivePing = new Observable<>(0L);
  private final Observable<Long> transactionPing = new Observable<>(0L);
  private final Observable<Integer> ticks = new Observable<>(0);
  private final Observable<Double> cps = new Observable<>(0.0);
  private final Observable<Double> rate = new Observable<>(0.0);
  private final Observable<BoundingBox> boundingBox =
      new Observable<>(new BoundingBox(0, 0, 0, null));

  private final Observable<RotationUpdate> rotationUpdate =
      new Observable<>(new RotationUpdate(0, 0));
  private final Observable<PositionUpdate> positionUpdate =
      new Observable<>(new PositionUpdate(null, null, false));

  private final RotationManager rotationManager = new RotationManager(this);
  private final CheckManager checkManager = new CheckManager(this);
  private final ExemptManager exemptManager = new ExemptManager(this);
  private final PositionManager positionManager = new PositionManager(this);
  private final ActionManager actionManager = new ActionManager(this);
  private final ConnectionManager connectionManager = new ConnectionManager(this);
  private final VelocityManager velocityManager = new VelocityManager();

  public PlayerData(Player bukkitPlayer) {
    this.bukkitPlayer = bukkitPlayer;
    connection = NmsUtil.getPlayerConnection(bukkitPlayer);

    target.observe(
        (from, to) -> {
          if (from == null || to == null || from.getEntityId() != to.getEntityId()) {
            getTargetLocations().clear();
          }
        });
  }
}
