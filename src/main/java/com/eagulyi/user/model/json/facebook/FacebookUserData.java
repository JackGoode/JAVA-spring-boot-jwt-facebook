
package com.eagulyi.user.model.json.facebook;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "education",
        "work",
        "first_name",
        "last_name",
        "location",
        "id",
        "email"
})
public class FacebookUserData {

    @JsonProperty("education")
    private List<Education> education = new ArrayList<Education>();
    @JsonProperty("work")
    private List<Work> work = new ArrayList<Work>();
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("education")
    public List<Education> getEducation() {
        return education;
    }

    @JsonProperty("education")
    public void setEducation(List<Education> education) {
        this.education = education;
    }

    @JsonProperty("work")
    public List<Work> getWork() {
        return work;
    }

    @JsonProperty("work")
    public void setWork(List<Work> work) {
        this.work = work;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(education).append(work).append(firstName).append(lastName).append(location).append(id).append(email).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FacebookUserData) == false) {
            return false;
        }
        FacebookUserData rhs = ((FacebookUserData) other);
        return new EqualsBuilder().append(education, rhs.education).append(work, rhs.work).append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(location, rhs.location).append(id, rhs.id).append(email, rhs.email).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
