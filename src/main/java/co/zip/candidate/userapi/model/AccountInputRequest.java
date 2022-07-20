package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.model.enums.AccountType;
import co.zip.candidate.userapi.validator.ValidEmail;
import co.zip.candidate.userapi.validator.ValueOfEnum;
import lombok.Data;

@Data
public class AccountInputRequest {

    @ValidEmail
    private String email;

    @ValueOfEnum(enumClass = AccountType.class)
    private String accountType;
}