package com.mballem.curso.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mballem.curso.security.domain.Medico;
import com.mballem.curso.security.domain.Usuario;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{

	@Query("select m from Medico m where m.usuario.id = :id")
	Optional<Medico> findByUsuarioId(Long id);

//	@Query("select m from Medico m where m.usuario.email like :email")
//	Optional<Medico> findByUsuarioEmail(String email);
	
	@Query("select m from Medico m where m.usuario.email like :email")
	Optional<Medico> findByUsuarioEmail(@Param("email") String email);
	
}
