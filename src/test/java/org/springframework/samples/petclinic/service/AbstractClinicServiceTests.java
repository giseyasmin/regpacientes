/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Paciente;
import org.springframework.samples.petclinic.model.Visita;
import org.springframework.samples.petclinic.model.VisitaType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> Base class for {@link ClinicService} integration tests. </p> <p> Subclasses should specify Spring context
 * configuration using {@link ContextConfiguration @ContextConfiguration} annotation </p> <p>
 * AbstractclinicServiceTests and its subclasses benefit from the following services provided by the Spring
 * TestContext Framework: </p> <ul> <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li> <li><strong>Dependency Injection</strong> of test fixture instances, meaning that
 * we don't need to perform application context lookups. See the use of {@link Autowired @Autowired} on the <code>{@link
 * AbstractclinicServiceTests#clinicService clinicService}</code> instance variable, which uses autowiring <em>by
 * type</em>. <li><strong>Transaction management</strong>, meaning each test method is executed in its own transaction,
 * which is automatically rolled back by default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script. <li> An {@link org.springframework.context.ApplicationContext
 * ApplicationContext} is also inherited and can be used for explicit bean lookup if necessary. </li> </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public abstract class AbstractClinicServiceTests {

    @Autowired
    protected ClinicService clinicService;

    @Test
    @Transactional
    public void findOwners() {
        Collection<Paciente> pacientes = this.clinicService.findOwnerByLastName("Davis");
        assertEquals(2, pacientes.size());
        pacientes = this.clinicService.findOwnerByLastName("Daviss");
        assertEquals(0, pacientes.size());
    }

    @Test
    public void findSingleOwner() {
        Paciente owner1 = this.clinicService.findOwnerById(1);
        assertTrue(owner1.getLastName().startsWith("Franklin"));
        Paciente owner10 = this.clinicService.findOwnerById(10);
        assertEquals("Carlos", owner10.getFirstName());

        assertEquals(owner1.getPets().size(), 1);
    }

    @Test
    @Transactional
    public void insertOwner() {
        Collection<Paciente> pacientes = this.clinicService.findOwnerByLastName("Schultz");
        int found = pacientes.size();
        Paciente paciente = new Paciente();
        paciente.setFirstName("Sam");
        paciente.setLastName("Schultz");
        paciente.setAddress("4, Evans Street");
        paciente.setCity("Wollongong");
        paciente.setTelephone("4444444444");
        this.clinicService.saveOwner(paciente);
        Assert.assertNotEquals("Paciente Id should have been generated", paciente.getId().longValue(), 0);
        pacientes = this.clinicService.findOwnerByLastName("Schultz");
        assertEquals("Verifying number of owners after inserting a new one.", found + 1, pacientes.size());
    }

    @Test
    @Transactional
    public void updateOwner() throws Exception {
        Paciente o1 = this.clinicService.findOwnerById(1);
        String old = o1.getLastName();
        o1.setLastName(old + "X");
        this.clinicService.saveOwner(o1);
        o1 = this.clinicService.findOwnerById(1);
        assertEquals(old + "X", o1.getLastName());
    }

	@Test
	public void findPet() {
	    Collection<VisitaType> types = this.clinicService.findPetTypes();
	    Visita pet7 = this.clinicService.findPetById(7);
	    assertTrue(pet7.getName().startsWith("Samantha"));
	    assertEquals(EntityUtils.getById(types, VisitaType.class, 1).getId(), pet7.getType().getId());
	    assertEquals("Jean", pet7.getOwner().getFirstName());
	    Visita pet6 = this.clinicService.findPetById(6);
	    assertEquals("George", pet6.getName());
	    assertEquals(EntityUtils.getById(types, VisitaType.class, 4).getId(), pet6.getType().getId());
	    assertEquals("Peter", pet6.getOwner().getFirstName());
	}

	@Test
	public void getPetTypes() {
	    Collection<VisitaType> visitaTypes = this.clinicService.findPetTypes();
	
	    VisitaType petType1 = EntityUtils.getById(visitaTypes, VisitaType.class, 1);
	    assertEquals("cat", petType1.getName());
	    VisitaType petType4 = EntityUtils.getById(visitaTypes, VisitaType.class, 4);
	    assertEquals("snake", petType4.getName());
	}

	@Test
	@Transactional
	public void insertPet() {
	    Paciente owner6 = this.clinicService.findOwnerById(6);
	    int found = owner6.getPets().size();
	    Visita visita = new Visita();
	    visita.setName("bowser");
	    Collection<VisitaType> types = this.clinicService.findPetTypes();
	    visita.setType(EntityUtils.getById(types, VisitaType.class, 2));
	    visita.setBirthDate(new DateTime());
	    owner6.addPet(visita);
	    assertEquals(found + 1, owner6.getPets().size());
	    // both storePet and storeOwner are necessary to cover all ORM tools
	    this.clinicService.savePet(visita);
	    this.clinicService.saveOwner(owner6);
	    owner6 = this.clinicService.findOwnerById(6);
	    assertEquals(found + 1, owner6.getPets().size());
	    assertNotNull("Visita Id should have been generated", visita.getId());
	}

	@Test
	@Transactional
	public void updatePet() throws Exception {
	    Visita pet7 = this.clinicService.findPetById(7);
	    String old = pet7.getName();
	    pet7.setName(old + "X");
	    this.clinicService.savePet(pet7);
	    pet7 = this.clinicService.findPetById(7);
	    assertEquals(old + "X", pet7.getName());
	}

	@Test
	public void findVets() {
	    Collection<Vet> vets = this.clinicService.findVets();
	
	    Vet v1 = EntityUtils.getById(vets, Vet.class, 2);
	    assertEquals("Leary", v1.getLastName());
	    assertEquals(1, v1.getNrOfSpecialties());
	    assertEquals("radiology", (v1.getSpecialties().get(0)).getName());
	    Vet v2 = EntityUtils.getById(vets, Vet.class, 3);
	    assertEquals("Douglas", v2.getLastName());
	    assertEquals(2, v2.getNrOfSpecialties());
	    assertEquals("dentistry", (v2.getSpecialties().get(0)).getName());
	    assertEquals("surgery", (v2.getSpecialties().get(1)).getName());
	}

	@Test
	@Transactional
	public void insertVisit() {
	    Visita pet7 = this.clinicService.findPetById(7);
	    int found = pet7.getVisits().size();
	    Visit visit = new Visit();
	    pet7.addVisit(visit);
	    visit.setDescription("test");
	    // both storeVisit and storePet are necessary to cover all ORM tools
	    this.clinicService.saveVisit(visit);
	    this.clinicService.savePet(pet7);
	    pet7 = this.clinicService.findPetById(7);
	    assertEquals(found + 1, pet7.getVisits().size());
	    assertNotNull("Visit Id should have been generated", visit.getId());
	}


}
