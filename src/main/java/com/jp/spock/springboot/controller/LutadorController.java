package com.jp.spock.springboot.controller;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jp.spock.springboot.entity.Lutador;
import com.jp.spock.springboot.repository.LutadorRepository;
import com.jp.spock.springboot.service.LutadorService;

@RestController
@RequestMapping("/lutador")
public class LutadorController {

	@Autowired
	private LutadorService service;

	@Autowired
	private LutadorRepository repository;

	@Value("${msg.erro.cadastro}")
	private String msgErroCadastro;

	@GetMapping("/melhor-lutador")
	public ResponseEntity getLutadorMaisVitorias() {
		try {
			Lutador lutador =  this.service.findMelhorLutador();
			return ResponseEntity.ok(lutador);
		} catch (NoResultException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping
	public ResponseEntity getLuatdores() {
		return this.repository.count() == 0L
				? ResponseEntity.noContent().build()
				: ResponseEntity.ok(this.repository.findAll());
	}
	
	@PostMapping
	public ResponseEntity criarLutador(@RequestBody Lutador lutador) {
		try {
			this.repository.save(lutador);
			return ResponseEntity.status(HttpStatus.CREATED).body(lutador);
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(msgErroCadastro);
		}
	}
}
