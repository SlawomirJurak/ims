package pl.sgnit.ims.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AssignedProcessValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AssignedProcess {
    String message() default "{pl.sgnit.ims.validator.assignedProcess}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
