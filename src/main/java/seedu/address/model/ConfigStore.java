package seedu.address.model;

/**
 * Wraps all Configuration data
 */
public class ConfigStore {

  private byte[] configData;

  public void addConfigData (Config config){
      configData = config.getConfigData();
  }

  public byte[] getConfigData(){
    return configData;
  }

}
