package org.alkemy.Alkemytestjava.Repositories;

import org.alkemy.Alkemytestjava.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByDni(String dni);
}