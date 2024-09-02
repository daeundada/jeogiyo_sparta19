package sparta.jeogiyo.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    CUSTOMER("CUSTOMER"),
    OWNER("OWNER"),
    MASTER("MASTER");

    private final String roleName;

    UserRoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
