package com.eagulyi.user.model.json.facebook;

import com.eagulyi.user.entity.Role;
import com.eagulyi.user.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugene on 5/30/17.
 */
public class UserUtil {

    public static List<UserRole> getDefaultRoles(String username) {
        List<UserRole> roles = new ArrayList<>();
        UserRole.Id userRoleId = new UserRole.Id(username, Role.MEMBER);
        UserRole role = new UserRole();
        role.setId(userRoleId);
        roles.add(role);
        return roles;
    }
}
