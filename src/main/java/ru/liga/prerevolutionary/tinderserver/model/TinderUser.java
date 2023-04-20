package ru.liga.prerevolutionary.tinderserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "TB_TINDER_USER")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TinderUser extends BaseEntity {
    @Column(name = "chat_id", nullable = false, unique = true)
    @NotEmpty
    String chatId;
    @Column(name = "name")
    @NotEmpty
    String name;
    @Column(name = "header")
    String header;
    @Column(name = "description")
    String description;
    @Column(name = "sex")
    String sex;
    @Column(name = "preference")
    String preference;
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    Date registered;
    @Column(name = "updated")
    Date updated;
    @OneToOne
    @JoinColumn(name = "last_viewed_id", referencedColumnName = "id")
    TinderUser lastViewedUser;
    @OneToOne
    @JoinColumn(name = "last_found_id", referencedColumnName = "id")
    TinderUser lastFoundUser;
}
