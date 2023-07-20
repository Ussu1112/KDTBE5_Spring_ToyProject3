package fastcampus.group9.toyproject3.user;

import fastcampus.group9.toyproject3._core.utils.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_tb")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long Id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 120)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean isBlacked;

    @Builder
    public User(Long id, String username, String password, String email, String nickname, UserRole role, boolean isBlacked) {
        Id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.isBlacked = isBlacked;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }




}
