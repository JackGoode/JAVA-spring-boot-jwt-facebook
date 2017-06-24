
package com.eagulyi.user.model.json.signup;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "institution",
    "speciality",
    "year"
})
public class EducationList {

    @JsonProperty("institution")
    private String institution;
    @JsonProperty("speciality")
    private String speciality;
    @JsonProperty("year")
    private String year;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("institution")
    public String getInstitution() {
        return institution;
    }

    @JsonProperty("institution")
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @JsonProperty("speciality")
    public String getSpeciality() {
        return speciality;
    }

    @JsonProperty("speciality")
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @JsonProperty("year")
    public String getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(String year) {
        this.year = year;
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
        return new HashCodeBuilder().append(institution).append(speciality).append(year).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EducationList)) {
            return false;
        }
        EducationList rhs = ((EducationList) other);
        return new EqualsBuilder().append(institution, rhs.institution).append(speciality, rhs.speciality).append(year, rhs.year).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
