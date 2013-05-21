
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "pets")
public class Visita extends NamedEntity {

    @Column(name = "birth_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private DateTime birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private VisitaType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Paciente paciente;

  

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public DateTime getBirthDate() {
        return this.birthDate;
    }

    public void setType(VisitaType type) {
        this.type = type;
    }

    public VisitaType getType() {
        return this.type;
    }

    protected void setOwner(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getOwner() {
        return this.paciente;
    }

  
}
