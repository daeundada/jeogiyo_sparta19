package sparta.jeogiyo.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.jeogiyo.domain.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
