package com.onlineenergyutilityplatform.utilities;

import com.onlineenergyutilityplatform.db.model.Role;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.name();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        return Role.valueOf(s);
    }
}
