package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ConfigStore;

/** Indicates the CredentialStore in the model has changed*/
public class ConfigStoreChangedEvent extends BaseEvent{

  public final ConfigStore data;

  public ConfigStoreChangedEvent(ConfigStore data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Byte size of config data: " + data.getConfigData().length;
  }
}
