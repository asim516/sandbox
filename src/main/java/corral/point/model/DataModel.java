package corral.point.model;

import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.NonNull;


public abstract class DataModel<T> implements Serializable {

  protected static final String EMPTY_STRING = "";
  private static final long serialVersionUID = 2690474987626338206L;
  protected final T value;

  protected DataModel(@NonNull final T dataModelValue) {
    value = dataModelValue;
  }

  public static <S> Set<S> getValues(final Set<? extends DataModel<S>> dataModels) {
    Set<S> values = new HashSet<>();
    for (DataModel<S> p : dataModels) {
      if (p != null) {
        values.add(p.getValue());
      }
    }
    return values;
  }

  public static <S> List<S> getValues(final List<? extends DataModel<S>> dataModels) {
    List<S> values = new ArrayList<>();
    for (DataModel<S> p : dataModels) {
      if (p != null) {
        values.add(p.getValue());
      }
    }
    return values;
  }

  public static boolean isNullOrEmpty(final DataModel p) {
    return p == null || p.isEmpty();
  }

  public final T getValue() {
    return value;
  }

  public abstract boolean isEmpty();

  /**
   * Override equals method.
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DataModel that = (DataModel) o;

    return Objects.equal(this.value, that.value);
  }

  /**
   * get the hashcode value.
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  /**
   * Convert to string representation.
   */
  @Override
  public String toString() {
    return this.value.toString();
  }
}
