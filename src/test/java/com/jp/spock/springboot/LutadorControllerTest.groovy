package com.jp.spock.springboot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

import com.jp.spock.springboot.controller.LutadorController
import com.jp.spock.springboot.entity.Lutador
import com.jp.spock.springboot.repository.LutadorRepository
import com.jp.spock.springboot.service.LutadorService

import spock.lang.Specification
import spock.lang.Stepwise

@SpringBootTest(classes = Application.class)
@Stepwise
class LutadorControllerTest extends Specification {

	@Autowired
	LutadorController controller;
	
	@Autowired
	LutadorRepository repository;
	
	@Autowired
	LutadorService service;
	
	@Value('${msg.erro.cadastro}')
	private String msgErro;
	
	def "deve retornar status 204 e mensagem sem corpo quando nao existirem lutadores"() {
		when:
			def resposta = controller.getLuatdores();
			
		then:
			resposta.status == HttpStatus.NO_CONTENT
			!resposta.body
	}
	
	def 'deve retornar status 400 e mensagem correta quando falha em criar lutador'() {
		when:
			def	resposta = controller.criarLutador(new Lutador());
		then:
			resposta.statusCode == HttpStatus.BAD_REQUEST
			resposta.body == msgErro

	}
	
	def "deve retornar status 201 com o JSON correto quando cria um lutador"() {
		given:
			def lutador = new Lutador("Zé Ruela", 80, 1.9)
		when:
			def resposta = controller.criarLutador(lutador)
		then:
			resposta.status == HttpStatus.CREATED
			resposta.body.properties == lutador.properties
			
	}
	
	def "deve retornar 200 trazer todos os lutadores e no formato correto quando existirem lutadores"() {
		given:
			def quantidade = repository.count()
		when:
			def resposta = controller.getLuatdores()
		then:
			resposta.status == HttpStatus.OK
			resposta.body.size() == quantidade
		and:
			for(lutador in resposta.body) {
				lutador instanceof Lutador
			}
	}
}