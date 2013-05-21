
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Paciente;
import org.springframework.samples.petclinic.model.Visita;
import org.springframework.samples.petclinic.model.VisitaType;


public interface ClinicService {

    public Collection<VisitaType> findPetTypes() throws DataAccessException;

    public Paciente findOwnerById(int id) throws DataAccessException;

    public Visita findPetById(int id) throws DataAccessException;

    public void savePet(Visita visita) throws DataAccessException;

    public void saveOwner(Paciente paciente) throws DataAccessException;

    Collection<Paciente> findOwnerByLastName(String lastName) throws DataAccessException;

}
