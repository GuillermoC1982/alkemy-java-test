package org.alkemy.Alkemytestjava.Repositories;

import org.alkemy.Alkemytestjava.Models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubjetsRepository extends JpaRepository<Subject, Long> {
}
