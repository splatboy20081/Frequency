package xyz.elevated.frequency.check.type;

import xyz.elevated.frequency.check.Check;
import xyz.elevated.frequency.data.PlayerData;
import xyz.elevated.frequency.update.PositionUpdate;

public class PositionCheck extends Check<PositionUpdate> {

  public PositionCheck(PlayerData playerData) {
    super(playerData);
  }

  @Override
  public void process(PositionUpdate positionUpdate) {}
}
