package corral.point.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public final class CorralPointId extends DataModel<String> {

    public static final CorralPointId EMPTY = new CorralPointId(EMPTY_STRING);

    private CorralPointId(final String corralPointId) {
        super(corralPointId);
    }

    @Override
    public boolean isEmpty() {
        return EMPTY.equals(this);
    }


    public static CorralPointId fromString(@NonNull final String corralPointId) {
        return new CorralPointId(corralPointId);
    }
}
