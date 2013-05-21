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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * JUnit test for the {@link Paciente} class.
 *
 * @author Ken Krebs
 */
public class PacienteTests {

    @Test
    @Transactional
    public void testHasPet() {
        Paciente paciente = new Paciente();
        Visita fido = new Visita();
        fido.setName("Consulta");
        assertNull(paciente.getPet("Consulta"));
        assertNull(paciente.getPet("Consulta"));
        paciente.addPet(fido);
        assertEquals(fido, paciente.getPet("Consulta"));
        assertEquals(fido, paciente.getPet("Consulta"));
    }

}
