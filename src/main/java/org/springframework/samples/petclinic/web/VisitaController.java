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
package org.springframework.samples.petclinic.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Paciente;
import org.springframework.samples.petclinic.model.Visita;
import org.springframework.samples.petclinic.model.VisitaType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@SessionAttributes("pet")
public class VisitaController {

    private final ClinicService clinicService;


    @Autowired
    public VisitaController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @ModelAttribute("types")
    public Collection<VisitaType> populatePetTypes() {
        return this.clinicService.findPetTypes();
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/pacientes/{ownerId}/visitas/nuevo", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("ownerId") int ownerId, Model model) {
        Paciente paciente = this.clinicService.findOwnerById(ownerId);
        Visita visita = new Visita();
        paciente.addPet(visita);
        model.addAttribute("pet", visita);
        return "visitas/crearModificarVisitaForm";
    }

    @RequestMapping(value = "/pacientes/{ownerId}/visitas/nuevo", method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute("pet") Visita visita, BindingResult result, SessionStatus status) {
        new PetValidator().validate(visita, result);
        if (result.hasErrors()) {
            return "visitas/crearModificarVisitaForm";
        } else {
            this.clinicService.savePet(visita);
            status.setComplete();
            return "redirect:/pacientes/{ownerId}";
        }
    }

    @RequestMapping(value = "/pacientes/*/visitas/{petId}/modificar", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("petId") int petId, Model model) {
        Visita visita = this.clinicService.findPetById(petId);
        model.addAttribute("pet", visita);
        return "visitas/crearModificarVisitaForm";
    }

    @RequestMapping(value = "/pacientes/{ownerId}/visitas/{petId}/modificar", method = {RequestMethod.PUT, RequestMethod.POST})
    public String processUpdateForm(@ModelAttribute("pet") Visita visita, BindingResult result, SessionStatus status) {
        // we're not using @Valid annotation here because it is easier to define such validation rule in Java
        new PetValidator().validate(visita, result);
        if (result.hasErrors()) {
            return "visitas/crearModificarVisitaForm";
        } else {
            this.clinicService.savePet(visita);
            status.setComplete();
            return "redirect:/pacientes/{ownerId}";
        }
    }

}
