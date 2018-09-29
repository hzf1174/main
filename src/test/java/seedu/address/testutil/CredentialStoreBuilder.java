package seedu.address.testutil;

import seedu.address.model.credential.Credential;
import seedu.address.model.credential.CredentialStore;

public class CredentialStoreBuilder {

    private CredentialStore credentialStore;

    public CredentialStoreBuilder(){
        credentialStore = new CredentialStore();
    }

    public CredentialStoreBuilder(CredentialStore credentialStore) {
        this.credentialStore = credentialStore;
    }

    /**
     * Adds a new {@code Credential} to the {@code CredentialStore} that we are
     * building.
     */
    public CredentialStoreBuilder withCredentials(Credential cred) {
        credentialStore.addCredential(cred);
        return this;
    }

    public CredentialStore build() {
        return credentialStore;
    }
}
