package com.system.artworkspace.validation;

import com.system.artworkspace.user.UserEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@ConditionalOnClass(UserEntity.class)
public class UserValidator implements Validator {

    private final String apiKey = "1365lhqahv1r42ilsl2kg9cupp6jjr08tj50mkjv73g6dnnea9hcp";

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;

        if (user.getEmail() == null || !validateEmail(user.getEmail())) {
            errors.rejectValue("email", "email.invalid", "Invalid email format");
        }

        if (user.getEmail().endsWith(".ru")) {
            errors.rejectValue("email", "email.fromRussia", "Email domain must NOT be from russia (.ru)");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errors.rejectValue("password", "password.short", "Password must contain at least 6 characters");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "The 'username' field is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", "The 'firstName' field is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required", "The 'lastName' field is required");
    }



    public boolean validateEmail(String email) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = String.format("https://anyapi.io/api/v1/email?apiKey=%s&email=%s", apiKey, email);
        RequestEntity<Void> request = RequestEntity.get(resourceUrl)
                .accept(MediaType.APPLICATION_JSON).build();

        try {
            ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
            Map<String, Object> jsonResponse = restTemplate.exchange(request, responseType).getBody();

            if (jsonResponse != null && jsonResponse.containsKey("validators")) {
                Map<String, Object> validators = (Map<String, Object>) jsonResponse.get("validators");

                for (Map.Entry<String, Object> entry : validators.entrySet()) {
                    if (entry.getValue() instanceof Map) {
                        Map<String, Boolean> validator = (Map<String, Boolean>) entry.getValue();
                        for (Boolean valid : validator.values()) {
                            if (!valid) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Email validation failed: " + e.getMessage());
        }
    }
}
