package com.alemcar.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alemcar.bookstore.domain.Categoria;
import com.alemcar.bookstore.dtos.CategoriaDTO;
import com.alemcar.bookstore.repositories.CategoriaRepository;
import com.alemcar.bookstore.service.exceptions.DataIntegrityViolationException;
import com.alemcar.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Categoria create(Categoria obj) {
		obj.setId(null);
		
		return repository.save(obj);
	}
	
	public Categoria update(Integer id, CategoriaDTO objDto) {
		Categoria obj = findById(id);
		obj.setNome(objDto.getNome());
		obj.setDescricao(objDto.getDescricao());
		
		return repository.save(obj);
	}
	
	public void delete(Integer id) {
		findById(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Categoria não pode ser deletada! ... Possui itens associados");
		}
	}
	
}
