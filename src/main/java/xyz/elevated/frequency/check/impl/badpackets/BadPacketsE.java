package xyz.elevated.frequency.check.impl.badpackets;

import xyz.elevated.frequency.check.CheckData;
import xyz.elevated.frequency.check.type.PostCheck;
import xyz.elevated.frequency.data.PlayerData;
import xyz.elevated.frequency.wrapper.impl.client.WrappedPlayInHeldItemSlot;

@CheckData(name = "BadPackets (E)")
public final class BadPacketsE extends PostCheck {

  public BadPacketsE(PlayerData playerData) {
    super(playerData, WrappedPlayInHeldItemSlot.class);
  }

  @Override
  public void process(Object object) {
    boolean post = isPost(object);

    if (post) fail();
  }
}
