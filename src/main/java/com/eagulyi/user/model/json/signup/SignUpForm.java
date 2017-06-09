
package com.eagulyi.user.model.json.signup;

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
        "name",
        "email",
        "country",
        "city",
        "passion",
        "educationList",
        "workList",
        "helpProposition",
        "addedProposition",
        "interests",
        "facebookP",
        "linkedinP",
        "contact"
})
public class SignUpForm {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("country")
    private String country;
    @JsonProperty("city")
    private String city;
    @JsonProperty("passion")
    private Object passion;
    @JsonProperty("educationList")
    private List<EducationList> educationList = new ArrayList<EducationList>();
    @JsonProperty("workList")
    private List<WorkList> workList = new ArrayList<WorkList>();
    @JsonProperty("helpProposition")
    private Object helpProposition;
    @JsonProperty("addedProposition")
    private Object addedProposition;
    @JsonProperty("interests")
    private Object interests;
    @JsonProperty("facebookP")
    private String facebookP;
    @JsonProperty("linkedinP")
    private String linkedinP;
    @JsonProperty("contact")
    private String contact;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("passion")
    public Object getPassion() {
        return passion;
    }

    @JsonProperty("passion")
    public void setPassion(Object passion) {
        this.passion = passion;
    }

    @JsonProperty("educationList")
    public List<EducationList> getEducationList() {
        return educationList;
    }

    @JsonProperty("educationList")
    public void setEducationList(List<EducationList> educationList) {
        this.educationList = educationList;
    }

    @JsonProperty("workList")
    public List<WorkList> getWorkList() {
        return workList;
    }

    @JsonProperty("workList")
    public void setWorkList(List<WorkList> workList) {
        this.workList = workList;
    }

    @JsonProperty("helpProposition")
    public Object getHelpProposition() {
        return helpProposition;
    }

    @JsonProperty("helpProposition")
    public void setHelpProposition(Object helpProposition) {
        this.helpProposition = helpProposition;
    }

    @JsonProperty("addedProposition")
    public Object getAddedProposition() {
        return addedProposition;
    }

    @JsonProperty("addedProposition")
    public void setAddedProposition(Object addedProposition) {
        this.addedProposition = addedProposition;
    }

    @JsonProperty("interests")
    public Object getInterests() {
        return interests;
    }

    @JsonProperty("interests")
    public void setInterests(Object interests) {
        this.interests = interests;
    }

    @JsonProperty("facebookP")
    public String getFacebookP() {
        return facebookP;
    }

    @JsonProperty("facebookP")
    public void setFacebookP(String facebookP) {
        this.facebookP = facebookP;
    }

    @JsonProperty("linkedinP")
    public String getLinkedinP() {
        return linkedinP;
    }

    @JsonProperty("linkedinP")
    public void setLinkedinP(String linkedinP) {
        this.linkedinP = linkedinP;
    }

    @JsonProperty("contact")
    public String getContact() {
        return contact;
    }

    @JsonProperty("contact")
    public void setContact(String contact) {
        this.contact = contact;
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
        return new HashCodeBuilder().append(firstName).append(lastName).append(email).append(country).append(city).append(passion).append(educationList).append(workList).append(helpProposition).append(addedProposition).append(interests).append(facebookP).append(linkedinP).append(contact).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SignUpForm)) {
            return false;
        }
        SignUpForm rhs = ((SignUpForm) other);
        return new EqualsBuilder().append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(email, rhs.email).append(country, rhs.country).append(city, rhs.city).append(passion, rhs.passion).append(educationList, rhs.educationList).append(workList, rhs.workList).append(helpProposition, rhs.helpProposition).append(addedProposition, rhs.addedProposition).append(interests, rhs.interests).append(facebookP, rhs.facebookP).append(linkedinP, rhs.linkedinP).append(contact, rhs.contact).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
