package com.eagulyi.facebook.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by eugene on 5/8/17.
 */
@Entity
@Table(name="USER_TOKEN")
public class UserToken {
    @Id
    @Column(name="fbId", unique = true)
    String fbId;
    @Column(name="username")
    String username;
    @Column(name="token")
    String token;

    public UserToken() {
    }

    public UserToken(String fbId, String token) {
        this.fbId = fbId;
        this.token = token;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
