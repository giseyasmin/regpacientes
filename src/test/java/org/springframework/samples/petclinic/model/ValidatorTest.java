package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Michael Isvy
 * Simple test to make sure that Bean Validation is working 
 * (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 *
 */
@ContextConfiguration(locations = {"ValidatorTest-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ValidatorTest {
	
	@Autowired
    private Validator validator;

	@Test
    public void emptyFirstName() {

        Persona persona = new Persona();
        persona.setFirstName("");
        persona.setLastName("smith");

        Set<ConstraintViolation<Persona>> constraintViolations = validator.validate(persona);

        Assert.assertEquals(1, constraintViolations.size());
        ConstraintViolation<Persona> violation =  constraintViolations.iterator().next();
        Assert.assertEquals(violation.getPropertyPath().toString(), "Nombre");
        Assert.assertEquals(violation.getMessage(), "may not be empty");
    }
	
}
