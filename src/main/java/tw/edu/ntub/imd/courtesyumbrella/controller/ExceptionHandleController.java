package tw.edu.ntub.imd.courtesyumbrella.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tw.edu.ntub.imd.birc.common.exception.DateParseException;
import tw.edu.ntub.imd.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.birc.common.exception.UnknownException;
import tw.edu.ntub.imd.birc.common.util.common.ClassUtils;
import tw.edu.ntub.imd.courtesyumbrella.exception.NullRequestBodyException;
import tw.edu.ntub.imd.courtesyumbrella.exception.form.InvalidFormDateFormatException;
import tw.edu.ntub.imd.courtesyumbrella.exception.form.InvalidFormNumberFormatException;
import tw.edu.ntub.imd.courtesyumbrella.exception.form.InvalidRequestFormatException;
import tw.edu.ntub.imd.courtesyumbrella.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.databaseconfig.exception.RepeatPersistException;

import java.lang.reflect.Field;
import java.util.List;

@ControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<String> handleProjectException(ProjectException e) {
        return ResponseEntityBuilder.error(e)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidFormatException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) e.getCause();
            List<JsonMappingException.Reference> referenceList = invalidFormatException.getPath();
            String message = "";
            if (referenceList.size() > 0) {
                JsonMappingException.Reference reference = referenceList.get(0);
                Object from = reference.getFrom();
                String fieldName = reference.getFieldName();
                Class<?> fromClass = from.getClass();
                Field declaredField = null;
                while (declaredField == null) {
                    try {
                        declaredField = fromClass.getDeclaredField(fieldName);
                    } catch (NoSuchFieldException ignored) {
                        fromClass = fromClass.getSuperclass();
                    }
                }
                String description;
                if (declaredField.isAnnotationPresent(Schema.class)) {
                    Schema schema = declaredField.getAnnotation(Schema.class);
                    description = schema.description();
                } else {
                    description = declaredField.getName();
                }
                if (ClassUtils.isNumber(invalidFormatException.getTargetType())) {
                    message = description + " - \"" + invalidFormatException.getValue() + "\"輸入的文字中包含非數字文字";
                } else {
                    throw new UnknownException(e);
                }
            }
            return ResponseEntityBuilder.error(new InvalidRequestFormatException(message))
                    .build();
        } else if (e.getRootCause() instanceof DateParseException) {
            DateParseException rootCause = (DateParseException) e.getRootCause();
            return ResponseEntityBuilder.error(new InvalidFormDateFormatException(rootCause))
                    .build();
        } else if (e.getRootCause() instanceof NumberFormatException) {
            NumberFormatException rootCause = (NumberFormatException) e.getRootCause();
            return ResponseEntityBuilder.error(new InvalidFormNumberFormatException(rootCause))
                    .build();
        } else {
            return ResponseEntityBuilder.error(new NullRequestBodyException(e))
                    .build();
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        e.printStackTrace();
        return ResponseEntityBuilder.error(new RepeatPersistException("重複新增"))
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(Exception e) {
        return ResponseEntityBuilder.error(new UnknownException(e))
                .build();
    }
}
