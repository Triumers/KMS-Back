package org.triumers.kmsback.auth.command.domain.aggregate.enums;

import jakarta.persistence.AttributeConverter;

public class UserRoleConverter implements AttributeConverter<UserRole, String> {// Git 충돌 복구용 주석

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return userRole.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        return UserRole.valueOf(dbData);
    }
}
