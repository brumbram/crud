package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UserInputRequest {

    @NotNull
    private String name;

    @ValidEmail
    private String email;

    @NotNull
    private BigDecimal monthlySalary;

    @NotNull
    private BigDecimal monthlyExpense;

}
