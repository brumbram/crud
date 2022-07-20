package co.zip.candidate.userapi.model.entity;

import co.zip.candidate.userapi.model.enums.AccountType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;

@Getter
@Setter
@Entity(name = "zipaccount")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AccountId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(
            name = "zip_user_id",
            foreignKey = @ForeignKey(name = "fk_zip_user_zip_account"),
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
