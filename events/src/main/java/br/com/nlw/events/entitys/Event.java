package br.com.nlw.events.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tbl_event")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;
    @Column(name = "title", length = 225, nullable = false)
    private String title;
    @Column(name = "pretty_name",length = 50, nullable = false,unique = true)
    private String prettyName;
    @Column(name = "location" , length = 255,nullable = false)
    private String location;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name = "start_data")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
}
