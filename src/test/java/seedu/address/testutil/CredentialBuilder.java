package seedu.address.testutil;

import seedu.address.model.credential.Credential;
/**
 * A utility class to help with building Credential objects.
 */
public class CredentialBuilder {

    public static final String DEFAULT_USERNAME = "lewis";
    public static final String DEFAULT_PASSWORD = "hamilton44";
    public static final String DEFAULT_KEY = "randomKeyLewis44";

    private String username;
    private String password;
    private String key;

    public CredentialBuilder() {
        username = DEFAULT_USERNAME;
        password = DEFAULT_PASSWORD;
        key = DEFAULT_KEY;
    }

    /**
     * Initializes the CredentialBuilder with the data of {@code
     * credentialToCopy}.
     */
    public CredentialBuilder(Credential credentialToCopy){
        this.username = credentialToCopy.getUsername();
        this.password = credentialToCopy.getPassword();
        this.key = credentialToCopy.getKey();
    }

    /**
     * Sets the {@code username} of the {@code Credential} that we are building.
     */
    public CredentialBuilder withUsername(String withUsername){
        this.username = withUsername;
        return this;
    }

    /**
     * Sets the {@code password} of the {@code Credential} that we are building.
     */
    public CredentialBuilder withPassword(String withPassword){
        this.password = withPassword;
        return this;
    }

    /**
     * Sets the {@code key} of the {@code Credential} that we are building.
     */
    public CredentialBuilder withKey(String withKey){
        this.key = withKey;
        return this;
    }



    public Credential build(){
        return new Credential(this.username, this.password, this.key);
    }


}
