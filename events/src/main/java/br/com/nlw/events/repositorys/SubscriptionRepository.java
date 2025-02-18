package br.com.nlw.events.repositorys;

import br.com.nlw.events.entitys.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {
}
