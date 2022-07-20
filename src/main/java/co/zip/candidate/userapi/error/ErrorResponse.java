package co.zip.candidate.userapi.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ErrorResponse {

    private final List<ErrorDetail> errorDetail;
}