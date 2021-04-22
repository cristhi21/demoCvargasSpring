package com.cvargas.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvargas.demo.entitys.Person;

public interface PersonDao extends JpaRepository<Person,Long> {

}
