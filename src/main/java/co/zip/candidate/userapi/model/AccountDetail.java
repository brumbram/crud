package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.model.enums.AccountType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDetail {

    private Long accountId;

    private AccountType accountType;
}
