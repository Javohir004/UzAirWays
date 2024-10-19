package uz.jvh.uzairways.domain.enumerators;

import java.util.List;
import java.util.stream.Collectors;

public enum RolePermission {

    CREATE_FLIGHT ,
    CANCEL_FLIGHT ,
    EDIT_FLIGHT ,
    READ_FLIGHT ,

    CREATE_TICKET,
    DELETE_TICKET ,
    EDIT_TICKET ,
    READ_TICKET,

    READ_FLIGHT_HISTORY,

    CREATE_BOOKING,
    CANCEL_BOOKING ,
    EDIT_BOOKING ,

    CREATE_ADMIN ,
    EDIT_ADMIN ,
    READ_ADMIN ,
    DELETE_ADMIN ,
    DO_ALL;

    public static List<RolePermission> getFromList(List<String> values) {
        return values.stream().map(RolePermission::valueOf)
                .collect(Collectors.toList());
    }



}
