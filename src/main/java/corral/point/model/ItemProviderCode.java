package corral.point.model;

import lombok.NonNull;

public final class ItemProviderCode extends DataModel<String> implements
    Comparable<ItemProviderCode> {

  public static final ItemProviderCode EMPTY = new ItemProviderCode(EMPTY_STRING);

  private ItemProviderCode(String itemProviderCode) {
    super(itemProviderCode);
  }

  @Override
  public boolean isEmpty() {
    return EMPTY.equals(this);
  }

  @Override
  public int compareTo(ItemProviderCode other) {
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

  public static ItemProviderCode fromString(@NonNull String itemProviderCode) {
    return new ItemProviderCode(itemProviderCode);
  }
}
