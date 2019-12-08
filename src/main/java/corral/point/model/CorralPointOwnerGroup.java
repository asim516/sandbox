package corral.point.model;

import lombok.NonNull;

public final class CorralPointOwnerGroup extends DataModel<Integer> implements
    Comparable<CorralPointOwnerGroup> {

  public static final CorralPointOwnerGroup EMPTY = CorralPointOwnerGroup
      .fromInt(Integer.MIN_VALUE);
  public static final CorralPointOwnerGroup CNSRTM_cpOwnerGroup = CorralPointOwnerGroup.fromInt(1);

  private CorralPointOwnerGroup(Integer cpOwnerGroup) {
    super(cpOwnerGroup);
  }

  @Override
  public boolean isEmpty() {
    return EMPTY.equals(this);
  }

  @Override
  public int compareTo(CorralPointOwnerGroup other) {
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

  public static CorralPointOwnerGroup fromString(@NonNull String cpOwnerGroup) {
    return new CorralPointOwnerGroup(Integer.parseInt(cpOwnerGroup));
  }

  public static CorralPointOwnerGroup fromInt(int cpOwnerGroup) {
    return new CorralPointOwnerGroup(cpOwnerGroup);
  }
}
