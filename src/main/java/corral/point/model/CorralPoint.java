package corral.point.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import lombok.Data;

@Data
public class CorralPoint implements Comparable<CorralPoint> {

  public static final CorralPoint EMPTY = new CorralPoint(CorralPointOwnerGroup.EMPTY,
      CorralPointId.EMPTY);

  private CorralPointId cpId;
  private CorralPointOwnerGroup cpg;

  protected CorralPoint() {
    cpId = CorralPointId.EMPTY;
    cpg = CorralPointOwnerGroup.EMPTY;
  }

  @JsonCreator
  public CorralPoint(@JsonProperty("cpg") CorralPointOwnerGroup cpg,
      @JsonProperty("cpId") CorralPointId cpId) {
    this.cpId = cpId;
    this.cpg = cpg;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CorralPoint that = (CorralPoint) o;

    return Objects.equal(this.cpId, that.cpId)
        && Objects.equal(this.cpg, that.cpg);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(cpId, cpg);
  }

  @Override
  public String toString() {
    return String.format("%s-%s", cpg, cpId);
  }

  @Override
  public int compareTo(CorralPoint o) {
    return ComparisonChain
        .start()
        .compare(o.cpg, this.cpg)
        .compare(o.cpId, this.cpId)
        .result();
  }

  public static boolean isNullOrIncomplete(CorralPoint cp) {
    return cp == null || DataModel.isNullOrEmpty(cp.cpg) || DataModel.isNullOrEmpty(cp.cpId);
  }
}
