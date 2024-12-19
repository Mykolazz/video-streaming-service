package com.mykola.example.validation.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.mykola.example.validation.ImportFileValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ImportFileValidator.class)
public @interface ValidImportFile {
    String message() default "Video validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
