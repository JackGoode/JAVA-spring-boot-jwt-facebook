package com.eagulyi.user.model.json.signup;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by eugene on 6/16/17.
 */
public class JsonUserObject {
    @JsonProperty("first_name")
    protected String firstName;
    @JsonProperty("last_name")
    protected String lastName;
    @JsonProperty("email")
    protected String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
