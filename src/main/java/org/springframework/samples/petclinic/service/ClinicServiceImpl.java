
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Paciente;
import org.springframework.samples.petclinic.model.Visita;
import org.springframework.samples.petclinic.model.VisitaType;
import org.springframework.samples.petclinic.repository.PacienteRepository;
import org.springframework.samples.petclinic.repository.VisitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClinicServiceImpl implements ClinicService {

    private VisitaRepository visitaRepository;
   
    private PacienteRepository pacienteRepository;

    @Autowired
    public ClinicServiceImpl(VisitaRepository visitaRepository, PacienteRepository pacienteRepository) {
        this.visitaRepository = visitaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<VisitaType> findPetTypes() throws DataAccessException {
        return visitaRepository.findPetTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findOwnerById(int id) throws DataAccessException {
        return pacienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Paciente> findOwnerByLastName(String lastName) throws DataAccessException {
        return pacienteRepository.findByLastName(lastName);
    }

    @Override
    @Transactional
    public void saveOwner(Paciente paciente) throws DataAccessException {
        pacienteRepository.save(paciente);
    }


  

    @Override
    @Transactional(readOnly = true)
    public Visita findPetById(int id) throws DataAccessException {
        return visitaRepository.findById(id);
    }

    @Override
    @Transactional
    public void savePet(Visita visita) throws DataAccessException {
        visitaRepository.save(visita);
    }

   


}
