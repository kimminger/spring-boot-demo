package com.rohner.springbootdemo.repos;

import com.rohner.springbootdemo.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
