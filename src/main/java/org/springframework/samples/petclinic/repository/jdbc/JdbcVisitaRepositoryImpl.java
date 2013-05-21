
package org.springframework.samples.petclinic.repository.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Paciente;
import org.springframework.samples.petclinic.model.Visita;
import org.springframework.samples.petclinic.model.VisitaType;
import org.springframework.samples.petclinic.repository.PacienteRepository;
import org.springframework.samples.petclinic.repository.VisitaRepository;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVisitaRepositoryImpl implements VisitaRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertPet;

    private PacienteRepository pacienteRepository;

  

    @Autowired
    public JdbcVisitaRepositoryImpl(DataSource dataSource, PacienteRepository pacienteRepository) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertPet = new SimpleJdbcInsert(dataSource)
                .withTableName("pets")
                .usingGeneratedKeyColumns("id");

        this.pacienteRepository = pacienteRepository;
       
    }

    @Override
    public List<VisitaType> findPetTypes() throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        return this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM types ORDER BY name",
                params,
                ParameterizedBeanPropertyRowMapper.newInstance(VisitaType.class));
    }

    @Override
    public Visita findById(int id) throws DataAccessException {
        JdbcVisita pet;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            pet = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE id=:id",
                    params,
                    new JdbcVisitaRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Visita.class, new Integer(id));
        }
        Paciente paciente = this.pacienteRepository.findById(pet.getOwnerId());
        paciente.addPet(pet);
        pet.setType(EntityUtils.getById(findPetTypes(), VisitaType.class, pet.getTypeId()));

      
        return pet;
    }

    @Override
    public void save(Visita visita) throws DataAccessException {
        if (visita.isNew()) {
            Number newKey = this.insertPet.executeAndReturnKey(
                    createPetParameterSource(visita));
            visita.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE pets SET name=:name, birth_date=:birth_date, type_id=:type_id, " +
                            "owner_id=:owner_id WHERE id=:id",
                    createPetParameterSource(visita));
        }
    }


    private MapSqlParameterSource createPetParameterSource(Visita visita) {
        return new MapSqlParameterSource()
                .addValue("id", visita.getId())
                .addValue("name", visita.getName())
                .addValue("birth_date", visita.getBirthDate().toDate())
                .addValue("type_id", visita.getType().getId())
                .addValue("owner_id", visita.getOwner().getId());
    }

}
