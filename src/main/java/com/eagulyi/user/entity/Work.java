package com.eagulyi.user.entity;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.CascadeType.ALL;


@Entity
@Table(name = "WORK")
public class Work implements Comparable<Work> {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = ALL, targetEntity = Employer.class)
    private Employer employer;

    @ManyToOne(cascade = ALL, targetEntity = Position.class)
    private Position position;

    @Column(name = "start_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate startDate;

    @Column(name = "end_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate endDate;

    @ManyToOne(cascade = ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    public Work() {
    }

    public Work(String employer, String position, Location location, LocalDate startDate, LocalDate endDate) {
        this.employer = new Employer(employer);
        this.position = new Position(position);
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int compareTo(Work o) {
        return this.startDate.compareTo(o.getStartDate());
    }

    @Override
    public int hashCode() {
        return this.startDate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Work && this.getStartDate().equals(((Work) obj).getStartDate());
    }
}