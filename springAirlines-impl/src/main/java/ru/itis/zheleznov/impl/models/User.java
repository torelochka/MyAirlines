package ru.itis.zheleznov.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.ROLE_USER;
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private State state = State.STATE_ACTIVE;

    public enum State {
        STATE_ACTIVE, STATE_BANNED
    }

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    public boolean isAdmin() {
        return this.role == Role.ROLE_ADMIN;
    }

    public boolean isActive() { return this.state == State.STATE_ACTIVE; }
}
