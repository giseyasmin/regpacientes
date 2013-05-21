
package org.springframework.samples.petclinic.repository.jdbc;

import org.springframework.samples.petclinic.model.Visita;


class JdbcVisita extends Visita {

    private int typeId;

    private int ownerId;


    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

}
