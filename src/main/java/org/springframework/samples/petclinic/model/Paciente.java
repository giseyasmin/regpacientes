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
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@Table(name = "pacientes")
public class Paciente extends Persona {
    @Column(name = "address")
    @NotEmpty
    private String address;

    @Column(name = "city")
    @NotEmpty
    private String city;

    @Column(name = "telephone")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Visita> visitas;


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    protected void setPetsInternal(Set<Visita> visitas) {
        this.visitas = visitas;
    }

    protected Set<Visita> getPetsInternal() {
        if (this.visitas == null) {
            this.visitas = new HashSet<Visita>();
        }
        return this.visitas;
    }

    public List<Visita> getPets() {
        List<Visita> sortedPets = new ArrayList<Visita>(getPetsInternal());
        PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedPets);
    }

    public void addPet(Visita visita) {
        getPetsInternal().add(visita);
        visita.setOwner(this);
    }

    /**
     * Return the Visita with the given name, or null if none found for this Paciente.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Visita getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Visita with the given name, or null if none found for this Paciente.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Visita getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Visita visita : getPetsInternal()) {
            if (!ignoreNew || !visita.isNew()) {
                String compName = visita.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return visita;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("new", this.isNew())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName())
                .append("address", this.address)
                .append("city", this.city)
                .append("telephone", this.telephone)
                .toString();
    }
}
