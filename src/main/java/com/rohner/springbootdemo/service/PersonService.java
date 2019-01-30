package com.rohner.springbootdemo.service;

import com.rohner.springbootdemo.domain.Person;
import com.rohner.springbootdemo.repos.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    public Collection<Person> getAll() {
        return personRepository.findAll();
    }

    public Person createPerson(Person person) {

        setAddressIfNotNull(person);

        personRepository.save(person);

        LOG.info("Person saved successfully with id: {}", person.getId());

        return person;
    }

    private void setAddressIfNotNull(Person person) {
        if (person.getAddresses() != null) {
            person.getAddresses().stream()
                    .filter(Objects::nonNull)
                    .forEach(address -> address.setPerson(person)
                    );
        }
    }

    public Person updatePerson(Integer id, Person person) {
        Optional<Person> existingPerson = personRepository.findById(id);
        if (existingPerson.isPresent()) {
            personRepository.deleteById(id);
            LOG.info("Existing Person deleted successfully: {}", existingPerson.get().getId());

            setAddressIfNotNull(person);
            personRepository.save(person);
            LOG.info("Person updated successfully: {}", person.getId());
        }
        return person;
    }

    public Person deletePerson(Integer id) {
        Optional<Person> person = getPerson(id);
        person.ifPresent(p -> personRepository.delete(p));
        return person.get();
    }

    public Optional<Person> getPerson(Integer id) {
        return personRepository.findById(id);
    }
}
