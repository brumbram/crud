package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.model.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountDetailResponse {

    private String userName;

    private String email;

    private List<AccountDetail> accounts;
}
