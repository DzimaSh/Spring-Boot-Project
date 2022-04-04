package com.example.demo.dao;

import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PersonDAO {
    private final StudentRepository studentRepository;

    @Autowired
    public PersonDAO(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Person> getAllPeople(){
        return studentRepository.findAll();
    }
    public Person getPersonById(Integer id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new PersonNotFoundException("Person with id " + id + "doesn't exists"));
    }
    public void savePerson(Person person){
        studentRepository.save(person);
    }
    public void updatePerson(Integer id, Person person) {
        Person personToUpdate = this.getPersonById(id);
        personToUpdate.setName(person.getName());
        personToUpdate.setAge(person.getAge());
        personToUpdate.setEmail(personToUpdate.getEmail());
        studentRepository.save(personToUpdate);
    }
    public void deletePerson(Integer id){
        studentRepository.delete(this.getPersonById(id));
    }
}
