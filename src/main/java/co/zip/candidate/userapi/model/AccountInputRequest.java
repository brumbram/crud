package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.model.enums.AccountType;
import co.zip.candidate.userapi.validator.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountInputRequest {

    @NotNull
    private String email;

    @ValueOfEnum(enumClass = AccountType.class)
    private AccountType accountType;
}
