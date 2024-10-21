package com.system.artworkspace.validation.exhibition;

import com.system.artworkspace.exhibition.ExhibitionDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Calendar;

public class ExhibitionDateValidator implements ConstraintValidator<ValidExhibitionDates, ExhibitionDto> {

    @Override
    public void initialize(ValidExhibitionDates constraintAnnotation) {}

    @Override
    public boolean isValid(ExhibitionDto exhibitionDto, ConstraintValidatorContext context) {
        if (exhibitionDto == null) {
            return true;
        }

        if (exhibitionDto.getStartDate() == null || exhibitionDto.getEndDate() == null) {
            return false;
        }

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar start = Calendar.getInstance();
        start.setTime(exhibitionDto.getStartDate());

        Calendar end = Calendar.getInstance();
        end.setTime(exhibitionDto.getEndDate());

        return !start.after(end) && !(end.before(today));
    }
}

