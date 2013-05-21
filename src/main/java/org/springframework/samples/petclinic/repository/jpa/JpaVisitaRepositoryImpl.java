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
package org.springframework.samples.petclinic.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.samples.petclinic.model.Visita;
import org.springframework.samples.petclinic.model.VisitaType;
import org.springframework.samples.petclinic.repository.VisitaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the {@link VisitaRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @since 22.4.2006
 */
@Repository
public class JpaVisitaRepositoryImpl implements VisitaRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<VisitaType> findPetTypes() {
        return this.em.createQuery("SELECT ptype FROM VisitaType ptype ORDER BY ptype.name").getResultList();
    }

    @Override
    public Visita findById(int id) {
        return this.em.find(Visita.class, id);
    }

    @Override
    public void save(Visita visita) {
    	if (visita.getId() == null) {
    		this.em.persist(visita);     		
    	}
    	else {
    		this.em.merge(visita);    
    	}
    }

}
