
package com.eagulyi.security.model.token;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tokenData"
})
public class FacebookAccessTokenData {

    @JsonProperty("data")
    private FacebookTokenData tokenData;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("data")
    public FacebookTokenData getTokenData() {
        return tokenData;
    }

    @JsonProperty("data")
    public void setTokenData(FacebookTokenData tokenData) {
        this.tokenData = tokenData;
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
        return new HashCodeBuilder().append(tokenData).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FacebookAccessTokenData)) {
            return false;
        }
        FacebookAccessTokenData rhs = ((FacebookAccessTokenData) other);
        return new EqualsBuilder().append(tokenData, rhs.tokenData).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
