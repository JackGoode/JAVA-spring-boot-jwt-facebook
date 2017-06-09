
package com.eagulyi.user.entity;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "EDUCATION")
public class Education implements Comparable<Education> {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false, cascade = ALL)
    @JoinColumn(name = "school_id")
    private School school;

    @Column(name = "type")
    private String type;

    @Column(name = "graduation_year")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate graduationYear;


    @ManyToMany(targetEntity = Speciality.class, cascade = ALL)
    private Set<Speciality> specialities = new HashSet<>();

    public Education() {
    }

    public Education(String school, String type, LocalDate graduationYear, Set<String> specialties) {
        this.school = new School(school);
        this.type = type;
        this.graduationYear = graduationYear;
        specialties.forEach(e -> this.specialities.add(new Speciality(e)));
    }

    public Education(String school, LocalDate graduationYear, String speciality) {
        this.school = new School(school);
        this.graduationYear = graduationYear;
        this.specialities.add(new Speciality(speciality));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(LocalDate graduationYear) {
        this.graduationYear = graduationYear;
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    @Override
    public int compareTo(Education o) {
        return this.getGraduationYear().compareTo(o.getGraduationYear());
    }
}
