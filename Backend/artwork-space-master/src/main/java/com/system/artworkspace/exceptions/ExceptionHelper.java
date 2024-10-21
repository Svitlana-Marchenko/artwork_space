package com.system.artworkspace.exceptions;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Set;

public class ExceptionHelper {
    public static String formErrorMessage(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder message = new StringBuilder("Validation failed: ");
        for (ObjectError o : allErrors){
            message.append(o.getDefaultMessage()).append("\n");
        }
        return message.toString();
    }
    public static <T> String formErrorMessage(Set<ConstraintViolation<T>> violations) {
        StringBuilder message = new StringBuilder("Validation failed: ");
        for (ConstraintViolation<T> violation : violations) {
            message.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\n");
        }
        return message.toString();
    }
}
