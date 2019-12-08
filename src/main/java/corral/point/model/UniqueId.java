package corral.point.model;

import static com.google.common.base.Preconditions.checkArgument;

import corral.point.model.generator.UuidGenerator;

public final class UniqueId extends DataModel<String> implements Comparable<UniqueId> {

  public static final UniqueId EMPTY = new UniqueId(EMPTY_STRING);

  private UniqueId(String uuid) {
    super(uuid);
  }

  @Override
  public boolean isEmpty() {
    return EMPTY.equals(this);
  }

  @Override
  public int compareTo(UniqueId other) {
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

  public static UniqueId fromString(String uuid) {
    return new UniqueId(uuid);
  }

  public static UniqueId create() {
    return new UniqueId(UuidGenerator.createId());
  }

  public static UniqueId create(int maxLength) {
    checkArgument(maxLength > 0, "The max length cannot be zero");
    String uuid = UuidGenerator.createId();
    return new UniqueId(uuid.substring(0, Math.min(maxLength, uuid.length())));
  }
}

