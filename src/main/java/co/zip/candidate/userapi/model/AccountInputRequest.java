package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.model.enums.AccountType;
import lombok.Data;

@Data
public class AccountInputRequest {

    private AccountType accountType;
}
