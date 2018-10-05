package co.christianblake.cblearning.sqlservice.repo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.christianblake.cblearning.sqlservice.rest.validation.EighteenOrOverValidation;
import lombok.Data;

@Entity
@Data
public class CBProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	private String surname;
	
	@EighteenOrOverValidation
	private LocalDate dob;
	
	@Email
	private String email;
}
