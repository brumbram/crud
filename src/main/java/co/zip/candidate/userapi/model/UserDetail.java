package co.zip.candidate.userapi.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class UserDetail {

    private String email;

    private String name;

    private BigDecimal monthlySalary;

    private BigDecimal monthlyExpense;
}
