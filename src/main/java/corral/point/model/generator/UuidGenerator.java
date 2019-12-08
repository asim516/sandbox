package corral.point.model.generator;

import java.util.UUID;

public class UuidGenerator {

  private UuidGenerator() {
  }

  public static String createId() {
    String uuid = UUID.randomUUID().toString();
    return uuid.replace("-", "");
  }
}
