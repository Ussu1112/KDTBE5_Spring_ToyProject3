package fastcampus.group9.toyproject3.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    SPROUT("새싹회원"),
    VIP("우수회원"),
    ADMIN("관리자");

    private final String name;
}
