package xyz.elevated.frequency.check.impl.invaliddirection;

import xyz.elevated.frequency.check.CheckData;
import xyz.elevated.frequency.check.type.PacketCheck;
import xyz.elevated.frequency.data.PlayerData;
import xyz.elevated.frequency.wrapper.impl.client.WrappedPlayInFlying;

@CheckData(name = "InvalidDirection")
public final class InvalidDirection extends PacketCheck {

  public InvalidDirection(PlayerData playerData) {
    super(playerData);
  }

  @Override
  public void process(Object object) {
    if (object instanceof WrappedPlayInFlying) {
      WrappedPlayInFlying wrapper = (WrappedPlayInFlying) object;

      if (wrapper.hasLook()) {
        float pitch = Math.abs(wrapper.getPitch());

        /*
         * Pitch will always be clamped between 90 and -90 (even when teleporting, etc). This threshold is here
         * because of some PvP client which messed it up on climbables, however it has since been fixed.
         */
        float threshold =
            playerData.getPositionManager().getTouchingClimbable().get() ? 91.11f : 90.f;

        if (pitch > threshold) {
          fail();
        }
      }
    }
  }
}
