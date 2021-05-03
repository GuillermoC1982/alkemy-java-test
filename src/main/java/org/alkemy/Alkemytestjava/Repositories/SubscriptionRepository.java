package org.alkemy.Alkemytestjava.Repositories;

import org.alkemy.Alkemytestjava.Models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
