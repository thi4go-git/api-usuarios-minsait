package com.dynns.cloudtecnologia.api.model.repository;

import com.dynns.cloudtecnologia.api.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.id <> :id")
    Optional<Usuario> findByEmailAndDifferentId(@Param("email") String email, @Param("id") Integer id);

}
