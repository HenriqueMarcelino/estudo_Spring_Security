package com.mballem.curso.security.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.mballem.curso.security.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.email like :email")
	Usuario findByEmail(@Param("email") String email);

	@Query("select distinct u from Usuario u "
			+ "join u.perfis p "
			+ "where u.email like :search% OR p.desc like :search%")
	public Page<Usuario> FindByEmailOrPerfil(@Param("search") String search , Pageable pageable);

	// Usamos o "IN" porque como estamos testando um lista de id é usado ele.


	@Query("select distinct u from Usuario u "
			+ "join u.perfis p "
			+ "where u.id = :usuarioId AND p.id IN :perfisId")
	 Optional<Usuario> findByIdAndPerfis(@Param("usuarioId") Long usuarioId, 
			 							 @Param("perfisId") Long[] perfisId );
	
}
