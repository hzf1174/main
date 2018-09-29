package seedu.address.model;

public class Config {

  private final byte[] configData;

  public Config (byte[] data) {
    this.configData = data;
  }

  public byte[] getConfigData() {
    return configData;
  }

}