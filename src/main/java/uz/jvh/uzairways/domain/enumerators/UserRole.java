package uz.jvh.uzairways.domain.enumerators;

import lombok.Getter;

import java.util.List;

public enum UserRole {
    ADMIN(List.of(
          RolePermission.CREATE_FLIGHT,
            RolePermission.EDIT_FLIGHT,
            RolePermission.CANCEL_FLIGHT,
            RolePermission.READ_FLIGHT_HISTORY,
            RolePermission.READ_FLIGHT,

            RolePermission.CREATE_TICKET,
            RolePermission.EDIT_TICKET,
            RolePermission.CANCEL_FLIGHT,
            RolePermission.DELETE_TICKET
    )),

    USER(List.of(
            RolePermission.CREATE_BOOKING,
            RolePermission.CANCEL_BOOKING,
            RolePermission.EDIT_BOOKING
    )),

    OWNER(List.of(
            RolePermission.CREATE_ADMIN,
            RolePermission.DO_ALL,
            RolePermission.DELETE_ADMIN,
            RolePermission.EDIT_ADMIN,
            RolePermission.READ_ADMIN
    ));

    @Getter
    private final List<RolePermission> allowedPermissions;


    UserRole(List<RolePermission> allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    }

}
