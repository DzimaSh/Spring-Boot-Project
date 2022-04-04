package com.example.demo.controller;

import com.example.demo.dao.PersonDAO;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String showAllPeople(Model model) {
        model.addAttribute("people", personDAO.getAllPeople());
        return "allPeople";
    }
    @GetMapping("/{id}")
    public String showPersonById(@PathVariable(name="id") Integer id, Model model) {
        model.addAttribute("person", personDAO.getPersonById(id));
        return "person";
    }
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "newPerson";
    }
    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "newPerson";
        personDAO.savePerson(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable(name="id") Integer id, Model model) {
        model.addAttribute("person", personDAO.getPersonById(id));
        return "editPerson";
    }
    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute @Valid Person person, BindingResult bindingResult, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors())
            return "editPerson";
        personDAO.updatePerson(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable Integer id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}
