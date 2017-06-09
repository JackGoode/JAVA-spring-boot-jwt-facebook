package com.eagulyi.user.entity;

import com.eagulyi.user.model.json.facebook.UserUtil;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static javax.persistence.CascadeType.ALL;

/**
 * Created by eugene on 5/25/17.
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "password")
    private String password;

    @Column(name = "creationDate")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private DataProvider dataProvider;

    @Column(name = "facebookId", unique = true)
    private String facebookId;

    @Column(name = "passion")
    private String passion;

    @Column(name = "helpProposition")
    private String helpProposition;

    @Column(name = "addedProposition")
    private String addedProposition;

    @Column(name = "interests")
    private String interests;

    @Column(name = "contact")
    private String contact;

    // TODO: fix duplication issue/ add constraint
    @ManyToOne(cascade = ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(targetEntity = Education.class, fetch = FetchType.LAZY, cascade = ALL)
    @OrderBy("graduation_year")
    private SortedSet<Education> educationItems = new TreeSet<>();

    @OneToMany(targetEntity = Work.class, fetch = FetchType.LAZY, cascade = ALL)
    @OrderBy("start_date")
    private SortedSet<Work> workItems = new TreeSet<>();

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private List<UserRole> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getPassion() {
        return passion;
    }

    public void setPassion(String passion) {
        this.passion = passion;
    }

    public String getHelpProposition() {
        return helpProposition;
    }

    public void setHelpProposition(String helpProposition) {
        this.helpProposition = helpProposition;
    }

    public String getAddedProposition() {
        return addedProposition;
    }

    public void setAddedProposition(String addedProposition) {
        this.addedProposition = addedProposition;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SortedSet<Education> getEducationItems() {
        return educationItems;
    }

    public void setEducationItems(SortedSet<Education> educationItems) {
        this.educationItems = educationItems;
    }

    public SortedSet<Work> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(SortedSet<Work> workItems) {
        this.workItems = workItems;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }


    public void setDefaultRoles() {
        setRoles(UserUtil.getDefaultRoles(this.username));
    }

    public void addEducationItem(Education education) {
        this.educationItems.add(education);
    }

    public void addWorkItem(Work work) {
        this.workItems.add(work);
    }
}
