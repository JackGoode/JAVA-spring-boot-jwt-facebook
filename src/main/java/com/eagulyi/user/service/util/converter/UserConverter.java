package com.eagulyi.user.service.util.converter;

import com.eagulyi.user.entity.User;
import com.eagulyi.user.model.json.signup.JsonUserObject;

/**
 * Created by eugene on 6/16/17.
 */
public interface UserConverter<T extends JsonUserObject> {
    User convert(T userObject);
}