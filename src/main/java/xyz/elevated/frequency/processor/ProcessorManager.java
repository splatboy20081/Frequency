package xyz.elevated.frequency.processor;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import java.util.Collection;
import xyz.elevated.frequency.processor.impl.IncomingPacketProcessor;
import xyz.elevated.frequency.processor.impl.OutgoingPacketProcessor;
import xyz.elevated.frequency.processor.type.Processor;

public final class ProcessorManager {
  private final ClassToInstanceMap<Processor<?>> processors;

  public ProcessorManager() {
    processors =
        new ImmutableClassToInstanceMap.Builder<Processor<?>>()
            .put(IncomingPacketProcessor.class, new IncomingPacketProcessor())
            .put(OutgoingPacketProcessor.class, new OutgoingPacketProcessor())
            .build();
  }

  /**
   * @param clazz - The class you want to get.
   * @param <T> - The raw class.
   * @return - The instance of the processor requested.
   */
  public final <T extends Processor<?>> T getProcessor(Class<T> clazz) {
    return processors.getInstance(clazz);
  }

  /** @return - Returns all the registered processors */
  public final Collection<Processor<?>> getProcessors() {
    return processors.values();
  }
}
