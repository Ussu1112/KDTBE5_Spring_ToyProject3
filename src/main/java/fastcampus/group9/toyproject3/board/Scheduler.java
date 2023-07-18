package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Scheduler {
    private final UserRepository userRepository;

    @Scheduled(fixedDelay = 60000)
    public void changeRole() {
    //새싹회원 이면서 게시글 수가 10개 이상인 회원에 대해 role=vip로 업데이트
    //1.위 조건 select
    //2.select된 entity들에 대해 setter로 수정
    //3. 수정한 entity들 save
    }
}
