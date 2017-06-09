package com.eagulyi.user.entity;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("ALL")
@Entity
@Table(name = "USER_ROLES")
public class UserRole  {
    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1322120000551624359L;

        @Column(name = "username")
        protected String username;

        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        protected Role role;

        public Id() { }

        public Id(String username, Role role) {
            this.username = username;
            this.role = role;
        }
    }

    @EmbeddedId
    Id id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", insertable=false, updatable=false)
    protected Role role;

    public Role getRole() {
        return role;
    }

    public void setId(Id id) {
        this.id = id;
        this.role = this.id.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
