package corral.point.model;

import lombok.NonNull;

public final class ItemClusterPriority extends DataModel<Integer> implements
    Comparable<ItemClusterPriority> {

  private ItemClusterPriority(Integer icPriority) {
    super(icPriority);
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public int compareTo(ItemClusterPriority other) {
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

  public static ItemClusterPriority fromString(@NonNull String icPriority) {
    return new ItemClusterPriority(Integer.parseInt(icPriority));
  }

  public static ItemClusterPriority fromInt(int icPriority) {
    return new ItemClusterPriority(icPriority);
  }
}
