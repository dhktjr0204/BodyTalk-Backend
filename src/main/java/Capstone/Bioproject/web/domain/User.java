package Capstone.Bioproject.web.domain;

import Capstone.Bioproject.web.domain.enums.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String provider;
    private String sex;
    private Long age;
    @Enumerated(EnumType.STRING)
    private Role role;;

    @Builder
    public User(Long id, String name, String email, String provider, Long age, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.provider=provider;
        this.age=age;
        this.role = role;
    }
    public User update(String name) {
        this.name = name;
        return this;
    }

    public void update(String name, Long age, String sex) {
        this.name = name;
        this.age=age;
        this.sex=sex;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
