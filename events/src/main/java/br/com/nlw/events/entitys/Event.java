package br.com.nlw.events.entitys;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name="tbl_event")
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventID;
    @Column(name = "title" ,length = 255,nullable = false)
    private String title;

    private String prettyName;
    private String location;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
