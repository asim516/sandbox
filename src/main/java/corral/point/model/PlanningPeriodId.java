package corral.point.model;

import corral.point.model.generator.UuidGenerator;
import lombok.NonNull;

public final class PlanningPeriodId extends DataModel<String> implements
    Comparable<PlanningPeriodId> {

  public static final PlanningPeriodId EMPTY = new PlanningPeriodId(EMPTY_STRING);

  private PlanningPeriodId(String planningPeriodId) {
    super(planningPeriodId);
  }

  @Override
  public boolean isEmpty() {
    return EMPTY.equals(this);
  }

  @Override
  public int compareTo(PlanningPeriodId other) {
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

  public static PlanningPeriodId create() {
    return new PlanningPeriodId(UuidGenerator.createId());
  }

  public static PlanningPeriodId fromString(@NonNull String planningPeriodId) {
    return new PlanningPeriodId(planningPeriodId);
  }
}
