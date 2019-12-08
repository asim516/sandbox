package corral.point.model;

import lombok.NonNull;

public final class SimulationId extends DataModel<String> implements Comparable<SimulationId> {

  public static final SimulationId EMPTY = new SimulationId("EMPTY");

  private SimulationId(String val) {
    super(val);
  }

  @Override
  public boolean isEmpty() {
    return EMPTY.equals(this);
  }

  @Override
  public int compareTo(SimulationId other) {
    return value.compareTo(other.value);
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public static SimulationId fromString(@NonNull String simulationId) {
    return new SimulationId(simulationId);
  }

  public static SimulationId create(int size) {
    String simulationIdString = UniqueId.create(size).getValue();
    return fromString(simulationIdString);
  }
}

