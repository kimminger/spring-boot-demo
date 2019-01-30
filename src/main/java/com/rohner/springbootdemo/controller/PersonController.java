package com.rohner.springbootdemo.controller;

import com.rohner.springbootdemo.domain.Person;
import com.rohner.springbootdemo.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@Api(value="demo", description="Demo Operations on Persons")
@RequestMapping(path = "resources")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "View a collection of available persons",response = Collection.class)
    @RequestMapping(path = "persons", method = RequestMethod.GET)
    public Collection<Person> getAll() {
        return personService.getAll();
    }

    @ApiOperation(value = "Search a person with an ID",response = Person.class)
    @RequestMapping(path = "persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable Integer id) {
        Optional<Person> person = personService.getPerson(id);
        if (!person.isPresent()) {
            throw new CustomEntityNotFoundException("Person with id " + String.valueOf(id) + " could not be found!");
        }
        return ResponseEntity.ok().body(person.get());

    }

    @ApiOperation(value = "Add a person")
    @RequestMapping(path = "person", method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person responsePerson = personService.createPerson(person);
        ServletUriComponentsBuilder servletUriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        URI location = servletUriComponentsBuilder.path("/{id}").buildAndExpand(responsePerson.getId()).toUri();
        LOGGER.info("New Person created at location: " + location);
        return ResponseEntity.created(location).body(responsePerson);
    }

    @ApiOperation(value = "Update a person")
    @RequestMapping(path = "persons/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(id, person);
        LOGGER.info("Person {} updated", id);
        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(updatedPerson);
    }

    @ApiOperation(value = "Delete a person")
    @RequestMapping(path = "persons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePerson(@PathVariable Integer id) {
        Person deletePerson = personService.deletePerson(id);
        return ResponseEntity.ok().body(deletePerson);
    }
}
