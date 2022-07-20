package co.zip.candidate.userapi.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "zipuser")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private BigDecimal monthlySalary;

    private BigDecimal monthlyExpense;

    @Getter
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    private final Set<Account> accounts = new HashSet<>();

    public void addAccount(Account account) {
        accounts.add(account);
        if(account.getUser() != this) {
            account.setUser(this);
        }
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        if (account.getUser() != null) {
            account.removeUser();
        }
    }

}
