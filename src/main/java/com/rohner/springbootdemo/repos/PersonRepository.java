package com.rohner.springbootdemo.repos;

import com.rohner.springbootdemo.domain.Address;
import com.rohner.springbootdemo.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
