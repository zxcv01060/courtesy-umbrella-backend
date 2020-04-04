package tw.edu.ntub.imd.courtesyumbrella.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tw.edu.ntub.imd.courtesyumbrella.utils.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.exception.ProjectException;
import tw.edu.ntub.imd.exception.UnknownException;

@ControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(Exception e) {
        return ResponseEntityBuilder.error(new UnknownException(e)).build();
    }

    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<String> handleProjectException(ProjectException e) {
        return ResponseEntityBuilder.error(e).build();
    }
}
