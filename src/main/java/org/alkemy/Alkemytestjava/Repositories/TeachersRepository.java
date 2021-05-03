package org.alkemy.Alkemytestjava.Repositories;

import org.alkemy.Alkemytestjava.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TeachersRepository extends JpaRepository<Teacher, Long> {
}
