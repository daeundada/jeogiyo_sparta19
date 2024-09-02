package sparta.jeogiyo.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

import java.util.UUID;

@Table(name = "p_orders")
@Getter
@Entity
public class Order extends BaseTimeEntity {

    @Id
    @UuidGenerator
    private UUID orderId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Setter
    private String requirement;

    @Setter
    private Boolean isDeleted = false;
}
