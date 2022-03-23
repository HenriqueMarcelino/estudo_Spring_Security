package com.mballem.curso.security.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mballem.curso.security.domain.Especialidade;
import com.mballem.curso.security.domain.Medico;
import com.mballem.curso.security.domain.Usuario;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{

	@Query("select m from Medico m where m.usuario.id = :id")
	Optional<Medico> findByUsuarioId(@Param("id")Long id);
	
	@Query("select m from Medico m where m.usuario.email like :email")
	Optional<Medico> findByUsuarioEmail(@Param("email") String email);
	
	@Query("select e from Especialidade e "
			+ "join e.medicos m "
			+ "where m.id = :perfisId")
	 Page<Especialidade> findByIdMedico(@Param("perfisId") Long perfisId , Pageable pageable);
	
	@Query("select distinct u from Usuario u "
			+ "join u.perfis p "
			+ "where u.id = :usuarioId AND p.id IN :perfisId")
	 Optional<Usuario> findByIdAndPerfis(@Param("usuarioId") Long usuarioId, 
			 							 @Param("perfisId") Long[] perfisId );
}
