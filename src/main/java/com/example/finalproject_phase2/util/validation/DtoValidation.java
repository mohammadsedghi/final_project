package com.example.finalproject_phase2.util.validation;

import com.example.finalproject_phase2.custom_exception.CustomException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;

import static org.hibernate.query.sqm.tree.SqmNode.log;

public class DtoValidation {
    public <T> boolean isValid(T object) {
        try {
            ValidatorFactory factory = Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory();

            Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate(object);
            if (violations.size() > 0) {
                for (ConstraintViolation<T> violation : violations) {
                    try {
                        throw new CustomException(violation.getMessage());
                    } catch (CustomException e) {
                        log.error(violation.getMessage());
                        throw new CustomException(e.getMessage());
                    }
                }
                factory.close();
                return false;
            } else {
                return true;
            }
        }catch (CustomException c){
           // System.out.println(c.getMessage());
            throw new CustomException(c.getMessage());
        }
    }
}
