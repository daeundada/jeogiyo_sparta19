package sparta.jeogiyo.domain.chat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

import java.util.UUID;

@Table(name = "p_chats")
@Getter
@Entity
public class Chat extends BaseTimeEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "chat_id", updatable = false, nullable = false) // 컬럼 설정
    private UUID chatId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Column(length = 100)
    private String question;

    @Setter
    @Column(length = 1000)
    private String answer;

    @Setter
    private Boolean isDeleted = false;
}
