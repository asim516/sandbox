package corral.point.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;


@Data
@Getter
public class InventoryEntry implements Comparable<InventoryEntry> {

  private static final LocalDate NO_SHRINK_DATE = new LocalDate(2112, 12, 31);

  public LocalDate shrinkDate;
  public double quantity;

  public InventoryEntry(LocalDate shrinkDate, long quantity) {
    this(shrinkDate, (double) quantity);
  }

  @JsonCreator
  public InventoryEntry(@JsonProperty("shrinkDate") LocalDate shrinkDate,
      @JsonProperty("quantity") double quantity) {
    if (shrinkDate == null) {
      shrinkDate = NO_SHRINK_DATE;
    }
    this.shrinkDate = shrinkDate;
    this.quantity = quantity;
  }

  public InventoryEntry(long quantity) {
    this.shrinkDate = NO_SHRINK_DATE;
    this.quantity = (double) quantity;
  }

  public InventoryEntry(InventoryEntry entry) {
    if (entry == null) {
      shrinkDate = NO_SHRINK_DATE;
      quantity = 0.0;
    } else {
      shrinkDate = entry.shrinkDate;
      quantity = entry.quantity;
    }
  }

  public static LocalDate getNoShrinkDate() {
    return NO_SHRINK_DATE;
  }

  @Override
  public int compareTo(InventoryEntry entry) {
    if (this == entry) {
      return 0;
    } else if (entry == null) {
      return 1;
    }

    int returnValue = shrinkDate.compareTo(entry.shrinkDate);
    if (returnValue == 0) {
      returnValue = Double.compare(quantity, entry.quantity);
    }
    return returnValue;
  }
}
