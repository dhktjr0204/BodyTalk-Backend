package Capstone.Bioproject.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    private String content;
    private Long disease;
    private LocalDate date;

    @Builder
    public Content(User user,String content, Long disease, LocalDate date) {
        this.user=user;
        this.content = content;
        this.disease = disease;
        this.date = date;
    }
}
