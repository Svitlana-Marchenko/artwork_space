package com.system.artworkspace.validation;

import com.system.artworkspace.user.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@ConditionalOnClass(User.class)
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Validation for the "email" field: Check for null and valid email format
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.rejectValue("email", "email.invalid", "Invalid email format");
        }

        // Validation for the "email" field: Check for russian host email
        if (user.getEmail().endsWith(".ru")) {
            errors.rejectValue("email", "email.fromRussia", "Email domain must NOT be from russia (.ru)");
        }

        // Validation for the "password" field: Check for null and minimum length
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errors.rejectValue("password", "password.short", "Password must contain at least 6 characters");
        }

        // Validation for the "username" field: Check for null or empty
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "The 'username' field is required");

        // Validation for the "firstName" field: Check for null or empty
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", "The 'firstName' field is required");

        // Validation for the "lastName" field: Check for null or empty
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required", "The 'lastName' field is required");
    }
}
