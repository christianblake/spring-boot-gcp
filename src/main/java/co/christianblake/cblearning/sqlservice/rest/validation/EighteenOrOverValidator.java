package co.christianblake.cblearning.sqlservice.rest.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EighteenOrOverValidator implements ConstraintValidator<EighteenOrOverValidation, LocalDate> {

	@Override
	public void initialize(EighteenOrOverValidation validation) {
	}

	@Override
	public boolean isValid(LocalDate validationField, ConstraintValidatorContext cxt) {
		
		LocalDate today = LocalDate.now();
		return (validationField != null) && (validationField.isBefore(today)) && (java.time.temporal.ChronoUnit.YEARS.between( validationField , today ) >= 18);
	}
}

