package Capstone.Bioproject.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="disease_id")
    private Long id;

    private String name;
    private String info;
    private String cause;
    private String type;

    @Builder
    public Disease(String name, String info, String cause, String type){
        this.name=name;
        this.info=info;
        this.cause=cause;
        this.type=type;
    }
}