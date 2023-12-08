package com.system.artworkspace.validation.exhibition;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExhibitionDateValidator.class)
@Documented
public @interface ValidExhibitionDates {
    String message() default "Invalid exhibition dates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

