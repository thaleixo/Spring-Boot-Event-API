package br.com.nlw.events.repositorys;

import br.com.nlw.events.entitys.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Event getEventByPrettyName(String prettyName);
}
