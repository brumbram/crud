package co.zip.candidate.userapi.util;

import co.zip.candidate.userapi.model.AccountDetail;
import co.zip.candidate.userapi.model.AccountDetailResponse;
import co.zip.candidate.userapi.model.UserDetail;
import co.zip.candidate.userapi.model.UserDetailResponse;
import co.zip.candidate.userapi.model.entity.Account;
import co.zip.candidate.userapi.model.entity.User;

public class ModelMapper {

    public static UserDetail mapToUserDetail(User user) {
        return UserDetail.builder()
                         .email(user.getEmail())
                         .name(user.getName())
                         .monthlySalary(user.getMonthlySalary())
                         .monthlyExpense(user.getMonthlyExpense())
                         .build();
    }

    public static AccountDetail mapToAccountDetail(Account account) {
        return AccountDetail.builder()
                            .accountId(account.getAccountId())
                            .accountType(account.getAccountType())
                            .build();
    }
}
