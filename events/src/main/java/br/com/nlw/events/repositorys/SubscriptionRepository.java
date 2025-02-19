package br.com.nlw.events.repositorys;

import br.com.nlw.events.DTO.SubscriptionRanking;
import br.com.nlw.events.entitys.Event;
import br.com.nlw.events.entitys.Subscription;
import br.com.nlw.events.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.concurrent.Flow;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {
    public Subscription findByEventAndSubscriber(Event event, User subscriber);

    @Query(value = "SELECT COUNT(s.subscription_number) AS quantidade, " +
            "s.indication_user_id, u.user_name " +
            "FROM tbl_subscription s " +
            "INNER JOIN tbl_user u ON s.indication_user_id = u.user_id " +
            "WHERE s.indication_user_id IS NOT NULL " +
            "AND s.event_id = :eventId " +
            "GROUP BY s.indication_user_id, u.user_name " +
            "ORDER BY quantidade DESC", nativeQuery = true)
    List<SubscriptionRanking> generateRanking(@Param("eventId") Integer eventId);
}
