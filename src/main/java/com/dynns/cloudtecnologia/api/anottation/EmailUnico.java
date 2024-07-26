package com.dynns.cloudtecnologia.api.anottation;

import com.dynns.cloudtecnologia.api.anottation.impl.EmailUnicoImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailUnicoImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnico {
    String message() default "O campo email já está sendo usado em outro cadastro!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
