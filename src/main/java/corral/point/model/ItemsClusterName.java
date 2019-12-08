package corral.point.model;

import lombok.NonNull;

public final class ItemsClusterName extends DataModel<String> implements
    Comparable<ItemsClusterName> {

  public static final ItemsClusterName EMPTY = new ItemsClusterName(EMPTY_STRING);

  private ItemsClusterName(String icName) {
    super(icName);
  }

  @Override
  public boolean isEmpty() {
    return EMPTY.equals(this);
  }

  @Override
  public int compareTo(ItemsClusterName other) {
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

  public static ItemsClusterName fromString(@NonNull String icName) {
    return new ItemsClusterName(icName);
  }
}
