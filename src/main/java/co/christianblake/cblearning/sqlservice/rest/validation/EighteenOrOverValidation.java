package co.christianblake.cblearning.sqlservice.rest.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = EighteenOrOverValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EighteenOrOverValidation {
	String message() default "Must be 18 or over.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
