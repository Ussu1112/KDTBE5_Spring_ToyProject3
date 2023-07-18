package fastcampus.group9.toyproject3.user;

import fastcampus.group9.toyproject3._core.utils.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 120) // 패스워드 인코딩(BCrypt)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role; // 새싹회원(SPROUT), 우수회원(VIP)

    @ColumnDefault("false")
    private Boolean is_blacked; // true, false

    @Builder
    public User(int id, String username, String password, String email, String nickname, UserRole role, Boolean is_blacked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.is_blacked = is_blacked;
    }
}
