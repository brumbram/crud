package co.zip.candidate.userapi.model.entity;

import co.zip.candidate.userapi.model.enums.AccountType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;

@Data
@Entity
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String AccountId;

    @Column(nullable = false)
    private AccountType accountType;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_account"),
            nullable = false
    )
    private User user;


    public void setUser(User user) {
        if (this.getUser() != user) {
            removeUser();
            this.user = user;
            user.addAccount(this);

        }
    }

    public void removeUser() {
        if (this.getUser() != null) {
            User previousUser = this.getUser();
            this.user = null;
            previousUser.removeAccount(this);
        }
    }

}
