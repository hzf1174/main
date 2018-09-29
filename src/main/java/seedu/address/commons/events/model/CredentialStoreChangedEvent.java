package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.credential.ReadOnlyCredentialStore;

/** Indicates the CredentialStore in the model has changed*/
public class CredentialStoreChangedEvent extends BaseEvent {

    public final ReadOnlyCredentialStore data;

    public CredentialStoreChangedEvent(ReadOnlyCredentialStore data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "No. of Credentials: " + data.getCredentials().size();
    }
}
