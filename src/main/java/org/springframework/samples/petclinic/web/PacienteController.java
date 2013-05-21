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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Paciente;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes(types = Paciente.class)
public class PacienteController {

    private final ClinicService clinicService;


    @Autowired
    public PacienteController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/pacientes/nuevo", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Paciente paciente = new Paciente();
        model.addAttribute(paciente);
        return "pacientes/crearModificarPacienteForm";
    }

    @RequestMapping(value = "/pacientes/nuevo", method = RequestMethod.POST)
    public String processCreationForm(@Valid Paciente paciente, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "pacientes/crearModificarPacienteForm";
        } else {
            this.clinicService.saveOwner(paciente);
            status.setComplete();
            return "redirect:/pacientes/" + paciente.getId();
        }
    }

    @RequestMapping(value = "/pacientes/integrantes", method = RequestMethod.GET)
    public String initFindForm(Model model) {
        model.addAttribute("owner", new Paciente());
        return "pacientes/findOwners";
    }
    
  
    

    @RequestMapping(value = "/pacientes", method = RequestMethod.GET)
    public String processFindForm(Paciente paciente, BindingResult result, Model model) {

        // allow parameterless GET request for /owners to return all records
        if (paciente.getLastName() == null) {
            paciente.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Paciente> results = this.clinicService.findOwnerByLastName(paciente.getLastName());
        if (results.size() < 1) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "pacientes/findOwners";
        }
        if (results.size() > 1) {
            // multiple owners found
            model.addAttribute("selections", results);
            return "pacientes/listadoPacientes";
        } else {
            // 1 owner found
            paciente = results.iterator().next();
            return "redirect:/pacientes/" + paciente.getId();
        }
    }

    @RequestMapping(value = "/pacientes/{ownerId}/modificar", method = RequestMethod.GET)
    public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
        Paciente paciente = this.clinicService.findOwnerById(ownerId);
        model.addAttribute(paciente);
        return "pacientes/crearModificarPacienteForm";
    }

    @RequestMapping(value = "/pacientes/{ownerId}/modificar", method = RequestMethod.PUT)
    public String processUpdateOwnerForm(@Valid Paciente paciente, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "pacientes/crearModificarPacienteForm";
        } else {
            this.clinicService.saveOwner(paciente);
            status.setComplete();
            return "redirect:/pacientes/{ownerId}";
        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/pacientes/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
        ModelAndView mav = new ModelAndView("pacientes/detallesPacientes");
        mav.addObject(this.clinicService.findOwnerById(ownerId));
        return mav;
    }

}
