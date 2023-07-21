package fastcampus.group9.toyproject3.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    SPROUT("ROLE_SPROUT"),
    VIP("ROLE_VIP"),
    ADMIN("ROLE_ADMIN");

    private final String name;

}
