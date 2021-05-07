package org.alkemy.Alkemytestjava.Repositories;

import org.alkemy.Alkemytestjava.Models.Subject;
import org.alkemy.Alkemytestjava.Models.Subscription;
import org.alkemy.Alkemytestjava.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllBySubject(Subject subject);
}
