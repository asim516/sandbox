package corral.point.model;

import static com.google.common.collect.Sets.newTreeSet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Multimap;
import com.google.common.collect.RowSortedTable;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeMultimap;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import lombok.Data;
import lombok.Getter;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

@Data
@Getter
public final class InventoryForecast {

  // Multimap<ArrivalDate, InventoryEntry> where InventoryEntry has quantity and shrink date
  private final SortedSetMultimap<LocalDate, InventoryEntry> warehouseSupplyVariations;
  private final SortedSetMultimap<LocalDate, InventoryEntry> poQuantities;
  private final SortedSetMultimap<LocalDate, InventoryEntry> plannedTransshipQuantities;
  private Interval interval;

  @JsonCreator
  private InventoryForecast(//@JsonProperty("interval")
      Interval interval,
      @JsonProperty("warehouseSupplyVariations")
          Multimap<LocalDate, InventoryEntry> warehouseSupplyVariations,
      @JsonProperty("poQuantities")
          Multimap<LocalDate, InventoryEntry> poQuantities,
      @JsonProperty("plannedTransshipQuantities")
          Multimap<LocalDate, InventoryEntry> plannedTransshipQuantities) {
    this.interval = interval;
    this.warehouseSupplyVariations = TreeMultimap.create(warehouseSupplyVariations);
    this.poQuantities = TreeMultimap.create(poQuantities);
    this.plannedTransshipQuantities = TreeMultimap.create(plannedTransshipQuantities);
  }

  private InventoryForecast(Builder builder) {
    warehouseSupplyVariations = builder.warehouseSupplyVariations;
    poQuantities = builder.poQuantities;
    plannedTransshipQuantities = builder.plannedTransshipQuantities;
    interval = builder.interval;
  }

  public static InventoryForecast getEmptyForecast() {
    return builder(new LocalDate(0).toInterval()).build();
  }

  public static Builder builder(Interval val) {
    return new Builder(val);
  }

  public SortedSet<InventoryEntry> getWarehouseSupplyVariation(LocalDate day) {
    if (warehouseSupplyVariations.containsKey(day)) {
      return warehouseSupplyVariations.get(day);
    }
    return newTreeSet();
  }

  public SortedSet<InventoryEntry> getPoQuantity(LocalDate day) {
    if (poQuantities.containsKey(day)) {
      return poQuantities.get(day);
    }
    return newTreeSet();
  }

  public SortedSet<InventoryEntry> getPlannedTransshipPoQuantity(LocalDate day) {
    if (plannedTransshipQuantities.containsKey(day)) {
      return plannedTransshipQuantities.get(day);
    }
    return newTreeSet();
  }

  public void putPoQuantity(LocalDate availableDate, LocalDate shrinkDate, long poQuantity) {
    InventoryEntry entry = new InventoryEntry(shrinkDate, poQuantity);
    // Multimaps aren't backed by a multiset, so we have to be careful not to overwrite entries
    if (poQuantities.get(availableDate).contains(entry)) {
      double oldQuantity = entry.quantity;
      poQuantities.get(availableDate).remove(entry);
      entry = new InventoryEntry(shrinkDate, oldQuantity + poQuantity);
    }
    poQuantities.put(availableDate, entry);

    if (availableDate.isBefore(interval.getStart().toLocalDate())) {
      interval = new Interval(availableDate.toDateTimeAtStartOfDay(), interval.getEnd());
    } else if (availableDate.isAfter(interval.getEnd().toLocalDate())) {
      interval = new Interval(interval.getStart(), availableDate.toDateTimeAtStartOfDay());
    }
  }

  public double getPoQuantityBeforeDate(LocalDate date) {
    double quantity = 0.0;
    for (Map.Entry<LocalDate, InventoryEntry> entry : poQuantities.entries()) {
      if (entry.getKey().isAfter(date) || entry.getKey().equals(date)) {
        continue;
      }
      quantity += entry.getValue().quantity;
    }
    return quantity;
  }

  public static final class Builder {

    private Interval interval;

    // Multimap<ArrivalDate, InventoryEntry> where InventoryEntry has quantity and shrink date
    private SortedSetMultimap<LocalDate, InventoryEntry> warehouseSupplyVariations = TreeMultimap
        .create();
    private SortedSetMultimap<LocalDate, InventoryEntry> poQuantities = TreeMultimap.create();
    private SortedSetMultimap<LocalDate, InventoryEntry> plannedTransshipQuantities = TreeMultimap
        .create();

    private Builder(Interval val) {
      interval = val;
    }

    public Builder warehouseSupplyVariations(RowSortedTable<LocalDate, LocalDate, Double> val) {
      warehouseSupplyVariations = getMultimapFromTable(val);
      return this;
    }

    public Builder warehouseSupplyVariations(SortedSetMultimap<LocalDate, InventoryEntry> val) {
      warehouseSupplyVariations = TreeMultimap.create(val);
      return this;
    }

    public Builder poQuantities(RowSortedTable<LocalDate, LocalDate, Double> val) {
      poQuantities = getMultimapFromTable(val);
      return this;
    }

    public Builder poQuantities(SortedSetMultimap<LocalDate, InventoryEntry> val) {
      poQuantities = TreeMultimap.create(val);
      return this;
    }

    public Builder plannedTransshipPoQuantities(RowSortedTable<LocalDate, LocalDate, Double> val) {
      plannedTransshipQuantities = getMultimapFromTable(val);
      return this;
    }

    public Builder plannedPoQuantities(SortedSetMultimap<LocalDate, InventoryEntry> val) {
      plannedTransshipQuantities = TreeMultimap.create(val);
      return this;
    }

    public InventoryForecast build() {
      interval = expandInterval(interval, warehouseSupplyVariations.keySet());
      interval = expandInterval(interval, poQuantities.keySet());
      interval = expandInterval(interval, plannedTransshipQuantities.keySet());
      return new InventoryForecast(this);
    }

    private SortedSetMultimap<LocalDate, InventoryEntry> getMultimapFromTable(
        Table<LocalDate, LocalDate, Double> table) {
      SortedSetMultimap<LocalDate, InventoryEntry> multimap = TreeMultimap.create();
      for (Table.Cell<LocalDate, LocalDate, Double> cell : table.cellSet()) {
        multimap.put(cell.getRowKey(), new InventoryEntry(cell.getColumnKey(), cell.getValue()));
      }
      return multimap;
    }

    private Interval expandInterval(Interval givenInterval, Collection<LocalDate> dates) {
      for (LocalDate date : dates) {
        if (date.isBefore(givenInterval.getStart().toLocalDate())) {
          givenInterval = givenInterval.withStart(date.toDateTimeAtStartOfDay());
        } else if (date.isAfter(givenInterval.getEnd().toLocalDate())) {
          givenInterval = givenInterval.withEnd(date.toDateTimeAtStartOfDay());
        }
      }
      return givenInterval;
    }
  }
}