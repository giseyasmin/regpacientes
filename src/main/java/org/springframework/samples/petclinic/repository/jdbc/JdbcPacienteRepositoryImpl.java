
package org.springframework.samples.petclinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Paciente;
import org.springframework.samples.petclinic.model.Visita;
import org.springframework.samples.petclinic.model.VisitaType;
import org.springframework.samples.petclinic.repository.PacienteRepository;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPacienteRepositoryImpl implements PacienteRepository {

  
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertOwner;

    @Autowired
    public JdbcPacienteRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.insertOwner = new SimpleJdbcInsert(dataSource)
                .withTableName("owners")
                .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

       
    }


   @Override
    public Collection<Paciente> findByLastName(String lastName) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("lastName", lastName + "%");
        List<Paciente> pacientes = this.namedParameterJdbcTemplate.query(
                "SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE last_name like :lastName",
                params,
                ParameterizedBeanPropertyRowMapper.newInstance(Paciente.class)
        );
        loadOwnersPetsAndVisits(pacientes);
        return pacientes;
    }

     @Override
    public Paciente findById(int id) throws DataAccessException {
        Paciente paciente;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            paciente = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE id= :id",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Paciente.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Paciente.class, id);
        }
        loadPetsAndVisits(paciente);
        return paciente;
    }

    public void loadPetsAndVisits(final Paciente paciente) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", paciente.getId().intValue());
        final List<JdbcVisita> pets = this.namedParameterJdbcTemplate.query(
                "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE owner_id=:id",
                params,
                new JdbcVisitaRowMapper()
        );
       
        
    }

    @Override
    public void save(Paciente paciente) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(paciente);
        if (paciente.isNew()) {
            Number newKey = this.insertOwner.executeAndReturnKey(parameterSource);
            paciente.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE owners SET first_name=:firstName, last_name=:lastName, address=:address, " +
                            "city=:city, telephone=:telephone WHERE id=:id",
                    parameterSource);
        }
    }

    public Collection<VisitaType> getPetTypes() throws DataAccessException {
        return this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM types ORDER BY name", new HashMap<String, Object>(),
                ParameterizedBeanPropertyRowMapper.newInstance(VisitaType.class));
    }

    private void loadOwnersPetsAndVisits(List<Paciente> pacientes) {
        for (Paciente paciente : pacientes) {
            loadPetsAndVisits(paciente);
        }
    }


}
