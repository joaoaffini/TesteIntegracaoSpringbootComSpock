package com.jp.spock.springboot.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.spock.springboot.entity.Lutador;

@Service
public class LutadorService {

	@Autowired
	private EntityManager em;

	/*public Lutador findMelhorLutador() {
					return	(Lutador)	this.em.createQuery(
									"from	Lutador	order	by	vitorias	desc,	nocontest	asc, sderrotas	asc")
									.setMaxResults(1)
									.getSingleResult();
	}*/
	
	public Lutador findMelhorLutador() {
		
		return (Lutador) this.em.createQuery(" from Lutador order by vitorias desc, nocontest asc, sderrotas asc  ")
				.setMaxResults(1)
				.getSingleResult();
	}
}
