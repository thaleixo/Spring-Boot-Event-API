package br.com.nlw.events.services;

import br.com.nlw.events.DTO.SubscriptionRanking;
import br.com.nlw.events.DTO.SubscriptionRankingByUser;
import br.com.nlw.events.DTO.SubscriptionResponse;
import br.com.nlw.events.Exceptions.UserNotFoundException;
import br.com.nlw.events.Exceptions.EventNotFoundException;
import br.com.nlw.events.Exceptions.SubscriptionConflictException;
import br.com.nlw.events.entitys.Event;
import br.com.nlw.events.entitys.Subscription;
import br.com.nlw.events.entitys.User;
import br.com.nlw.events.repositorys.EventRepository;
import br.com.nlw.events.repositorys.SubscriptionRepository;
import br.com.nlw.events.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    public SubscriptionResponse newSubscription(String eventName, User user, Integer indicatorID) {
        Event event = eventRepository.getEventByPrettyName(eventName);
        User userRec = userRepository.findByEmail(user.getEmail());
        if (event == null){
            throw new EventNotFoundException("Event " + eventName+  " not found");
        }
        User indicator = null;
        if (indicatorID != null){
                indicator = userRepository.findById(indicatorID).orElse(null);
                if (indicator == null) {
                    throw new UserNotFoundException("User ID " + indicatorID + " not found");
                }
        }

        if(userRec == null){
            userRec = userRepository.save(user);
        }

        if (subscriptionRepository.findByEventAndSubscriber(event, userRec) != null){
            throw new SubscriptionConflictException("User " + user.getName() + " already has a subscriber in event " + eventName);
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(user);
        subscription.setIndication(indicator);
        subscriptionRepository.save(subscription);

        return new SubscriptionResponse(subscription.getSubscriptionNumber(),"http://CodeCraft.com/"+ event.getPrettyName() +"/"+subscription.getSubscriptionNumber());
    }


    public List<SubscriptionRanking> getRanking(String prettyName) {
        Event event = eventRepository.findByPrettyName(prettyName);
        if (event == null){
            throw new EventNotFoundException("Event " + prettyName + " not found");
        }
        return subscriptionRepository.generateRanking(event.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userID) {
        List<SubscriptionRanking> ranking = getRanking(prettyName);
        SubscriptionRanking rankingByUser = ranking.stream().filter(i->i.user_id().equals(userID)).findFirst().orElse(null);
        if (rankingByUser == null){
            throw new UserNotFoundException("User ID " + userID + " not found");
        }
        Integer userPosition = IntStream.range(0, ranking.size()).filter(
                pos-> ranking.get(pos).user_id().equals(userID)).findFirst().getAsInt();
        return new SubscriptionRankingByUser(rankingByUser,userPosition + 1);
    }
}


