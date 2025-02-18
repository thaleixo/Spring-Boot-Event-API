package br.com.nlw.events.services;

import br.com.nlw.events.entitys.Event;
import br.com.nlw.events.repositorys.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event addNewEvent(Event event) {
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "_"));
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventByPrettyName(String prettyName) {
        return eventRepository.getEventByPrettyName(prettyName);
    }
}
