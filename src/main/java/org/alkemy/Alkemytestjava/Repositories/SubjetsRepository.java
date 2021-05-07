package org.alkemy.Alkemytestjava.Repositories;

import org.alkemy.Alkemytestjava.Models.Subject;
import org.alkemy.Alkemytestjava.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SubjetsRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByTeacher(Teacher teacher);
}
