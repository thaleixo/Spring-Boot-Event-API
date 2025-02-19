package br.com.nlw.events.controllers;

import br.com.nlw.events.DTO.ErrorMessage;
import br.com.nlw.events.DTO.SubscriptionRanking;
import br.com.nlw.events.DTO.SubscriptionResponse;
import br.com.nlw.events.Exceptions.UserNotFoundException;
import br.com.nlw.events.Exceptions.EventNotFoundException;
import br.com.nlw.events.Exceptions.SubscriptionConflictException;
import br.com.nlw.events.entitys.User;
import br.com.nlw.events.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/{prettyName}", "/{prettyName}/{indicatorID}"})
    public ResponseEntity<?> newSubscription(@PathVariable String prettyName,
                                             @RequestBody User subscriber,
                                             @PathVariable(required = false) Integer indicatorID) {
        try {
            SubscriptionResponse subscription = subscriptionService.newSubscription(prettyName, subscriber, indicatorID);
            if (subscription != null) {
                return ResponseEntity.ok(subscription);
            }
        }catch (EventNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }catch (SubscriptionConflictException ex){
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
        }catch (UserNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{prettyName}/ranking")
    public ResponseEntity<?> generateRankingByEvent(@PathVariable String prettyName) {
        try {
            return ResponseEntity.ok(subscriptionService.getRanking(prettyName));
        }catch (EventNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }

    @GetMapping("/{prettyName}/ranking/{userID}")
    public ResponseEntity<?> generateRankingByEventAndUserID(@PathVariable String prettyName, @PathVariable Integer userID) {
        try{
            return ResponseEntity.ok(subscriptionService.getRankingByUser(prettyName, userID));
        }catch (UserNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }catch (EventNotFoundException ex){
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }

    }


}
