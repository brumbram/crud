package co.zip.candidate.userapi.error;

public class Constants {

    public static final String FIELD_EXCEPTION_NOT_NULL = "NotNull";

    public enum Errors {

        EMAIL_ADDRESS_ALREADY_EXISTS("0200", "Email already exists", "Email already exists"),
        EXPENSE_THRESHOLD_ERROR("0300", "Expense threshold error", "Monthly expense is higher than salary threshold"),
        USER_NOT_FOUND("0400", "User not found", "User not found"),
        ACCOUNT_ALREADY_EXISTS("0500", "Account already exist","Account already exist"),
        INVALID_EMAIL("1001","Invalid email address","Missing or invalid email address"),
        MISSING_FIELD_INPUT("1002","Missing value for required field","Missing value for required field %s"),
        GENERAL_ERROR("0100", "Unknown Error", "Unknown error occurred");

        private final String code;
        private final String title;
        private final String detail;

        Errors(String code, String title, String message) {
            this.code = code;
            this.title = title;
            this.detail = message;
        }

        public String getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }

        public String getDetail() {
            return detail;
        }
    }
}
