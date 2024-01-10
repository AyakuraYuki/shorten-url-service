package cc.ayakurayuki.shortenurl.interfaces.rest.advice;

import cc.ayakurayuki.extension.gson.AYGson;
import cc.ayakurayuki.shortenurl.infrastructure.errors.ServerCode;
import cc.ayakurayuki.shortenurl.infrastructure.errors.ServerError;
import cc.ayakurayuki.shortenurl.interfaces.rsp.BasicRsp;
import cc.ayakurayuki.shortenurl.interfaces.rsp.BasicView;
import com.google.common.collect.Lists;
import io.micrometer.core.instrument.config.validate.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:51
 */
@RestControllerAdvice
public class ExceptionAdvice {

  @Value("${spring.profiles.active}")
  private String profile;

  private boolean isTestEnv() {
    return !profile.equalsIgnoreCase("prod");
  }

  @ExceptionHandler(value = Exception.class)
  public BasicView<?> defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {

    int code;
    String message;

    if (ex instanceof ServerError serverError) {
      // 自定义异常

      code = serverError.getCode();
      message = serverError.getMessage();

    } else if (ex instanceof DataAccessException) {
      // 数据访问异常

      code = ServerCode.SERVER_ERROR.getCode();
      message = String.format("(%d) data access error", code);

    } else if (ex instanceof SQLException) {
      // SQL 异常

      code = ServerCode.SERVER_ERROR.getCode();
      message = String.format("(%d) data flow error", code);

    } else if (ex instanceof BindException bindException) {
      // 数据绑定异常

      List<String> errors = Lists.newArrayList();
      for (FieldError fieldError : bindException.getFieldErrors()) {
        errors.add(fieldError.getDefaultMessage());
      }
      code = ServerCode.BAD_REQUEST.getCode();
      message = String.format("(%d) data bind error", code);
      if (isTestEnv()) {
        message = AYGson.create().toJson(errors);
      }

    } else if (ex instanceof MissingServletRequestParameterException) {
      // 丢失请求参数异常

      code = ServerCode.BAD_REQUEST.getCode();
      message = ServerCode.BAD_REQUEST.getMessage();
      if (isTestEnv()) {
        message = ex.getMessage();
      }

    } else if (ex instanceof HttpRequestMethodNotSupportedException || ex instanceof NoHandlerFoundException) {
      // 不支持的请求方法 / 请求了不存在的接口

      code = 404;
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      message = "404 Not Found";

    } else {

      code = ServerCode.SERVER_ERROR.getCode();
      message = ex.getMessage();

    }

    return BasicView.create(code, message);
  }

  @ExceptionHandler({
      ValidationException.class,
      TypeMismatchException.class,
      ServletRequestBindingException.class,
      MissingPathVariableException.class,
      HttpMessageNotReadableException.class,
      MethodArgumentNotValidException.class,
      MissingServletRequestPartException.class,
      MultipartException.class
  })
  public BasicRsp illegalRequestException(Exception e) {
    return new BasicRsp(ServerCode.BAD_REQUEST);
  }

  @ExceptionHandler(Throwable.class)
  public BasicRsp throwable(Throwable e) {
    if (e instanceof ServerError serverError) {
      return new BasicRsp(serverError.getCode(), e.getMessage());
    }
    return new BasicRsp(ServerCode.SERVER_ERROR);
  }

  @ExceptionHandler(ClientAbortException.class)
  public BasicRsp clientAbortException(ClientAbortException e) {
    // ignore client abort request, null value will be ignored
    return null;
  }

}
