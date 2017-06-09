
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
    "school",
    "year",
    "concentration",
    "id",
    "type"
})
public class Education {

    @JsonProperty("school")
    private School school;
    @JsonProperty("year")
    private Year year;
    @JsonProperty("concentration")
    private List<Concentration> concentration = new ArrayList<Concentration>();
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("school")
    public School getSchool() {
        return school;
    }

    @JsonProperty("school")
    public void setSchool(School school) {
        this.school = school;
    }

    @JsonProperty("year")
    public Year getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(Year year) {
        this.year = year;
    }

    @JsonProperty("concentration")
    public List<Concentration> getConcentration() {
        return concentration;
    }

    @JsonProperty("concentration")
    public void setConcentration(List<Concentration> concentration) {
        this.concentration = concentration;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
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
        return new HashCodeBuilder().append(school).append(year).append(concentration).append(id).append(type).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Education) == false) {
            return false;
        }
        Education rhs = ((Education) other);
        return new EqualsBuilder().append(school, rhs.school).append(year, rhs.year).append(concentration, rhs.concentration).append(id, rhs.id).append(type, rhs.type).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
