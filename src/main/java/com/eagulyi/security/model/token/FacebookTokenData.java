
package com.eagulyi.security.model.token;

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
        "app_id",
        "application",
        "error",
        "expires_at",
        "is_valid",
        "scopes",
        "user_id"
})
public class FacebookTokenData {

    @JsonProperty("app_id")
    private String appId;
    @JsonProperty("application")
    private String application;
    @JsonProperty("error")
    private FacebookTokenError error;
    @JsonProperty("expires_at")
    private Integer expiresAt;
    @JsonProperty("is_valid")
    private Boolean isValid;
    @JsonProperty("scopes")
    private List<String> scopes = new ArrayList<>();
    @JsonProperty("user_id")
    private String userId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @JsonProperty("application")
    public String getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(String application) {
        this.application = application;
    }

    @JsonProperty("error")
    public FacebookTokenError getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(FacebookTokenError error) {
        this.error = error;
    }

    @JsonProperty("expires_at")
    public Integer getExpiresAt() {
        return expiresAt;
    }

    @JsonProperty("expires_at")
    public void setExpiresAt(Integer expiresAt) {
        this.expiresAt = expiresAt;
    }

    @JsonProperty("is_valid")
    public Boolean getIsValid() {
        return isValid;
    }

    @JsonProperty("is_valid")
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
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
        return new HashCodeBuilder().append(appId).append(application).append(error).append(expiresAt).append(isValid).append(scopes).append(userId).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FacebookTokenData)) {
            return false;
        }
        FacebookTokenData rhs = ((FacebookTokenData) other);
        return new EqualsBuilder().append(appId, rhs.appId).append(application, rhs.application).append(error, rhs.error).append(expiresAt, rhs.expiresAt).append(isValid, rhs.isValid).append(scopes, rhs.scopes).append(userId, rhs.userId).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
