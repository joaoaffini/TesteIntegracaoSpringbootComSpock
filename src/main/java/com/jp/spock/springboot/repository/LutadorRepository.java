package com.jp.spock.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jp.spock.springboot.entity.Lutador;

@Repository
public interface LutadorRepository extends JpaRepository<Lutador, Long> {

}
