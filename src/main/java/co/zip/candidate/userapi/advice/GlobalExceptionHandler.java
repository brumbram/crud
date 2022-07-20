package co.zip.candidate.userapi.advice;

import co.zip.candidate.userapi.error.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Handle invalid input data
   *
   * @param dataInputError
   * @return a {@code ErrorResponse}
   */
  @ExceptionHandler(value = InvalidInputError.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleInvalidInputError(
          InvalidInputError dataInputError) {
    log.error(dataInputError.getDetail(), dataInputError);
    return ResponseEntity.badRequest()
                         .body(
                                 new ErrorResponse(List.of(new ErrorDetail(
                                         dataInputError.getCode(),
                                         dataInputError.getTitle(),
                                         dataInputError.getDetail()
                                 ))));
  }

  /**
   * Handle invalid user token exception
   *
   * @param methodArgumentNotValidException
   * @return a {@code ErrorResponse}
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException methodArgumentNotValidException) {
    List<ErrorDetail> errors = methodArgumentNotValidException
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(x -> InvalidFieldMapper.toErrorDetails(x))
            .collect(Collectors.toList());

    //log.error(errors.stream().flatMap(x -> x.getDetail())., methodArgumentNotValidException);
    return ResponseEntity.badRequest()
                         .body(
                                 new ErrorResponse(errors));
  }

  /**
   * Handle unknown exception
   *
   * @param unknownException
   * @return a {@code ErrorResponse}
   */
  @ExceptionHandler(value = Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleUnknownException(Throwable unknownException) {
    log.error(unknownException.getMessage(), unknownException);
    return ResponseEntity.internalServerError()
                         .body(
                                 new ErrorResponse(List.of(new ErrorDetail(
                                         Constants.Errors.GENERAL_ERROR.getCode(),
                                         Constants.Errors.GENERAL_ERROR.getTitle(),
                                         Constants.Errors.GENERAL_ERROR.getDetail()))));
  }
}
