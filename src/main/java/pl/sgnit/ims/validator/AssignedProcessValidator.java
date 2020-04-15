package pl.sgnit.ims.validator;

import pl.sgnit.ims.model.Process;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AssignedProcessValidator implements ConstraintValidator<AssignedProcess, Process> {
    @Override
    public void initialize(AssignedProcess constraintAnnotation) {

    }

    @Override
    public boolean isValid(Process process, ConstraintValidatorContext constraintValidatorContext) {
        return process != null;
    }
}
