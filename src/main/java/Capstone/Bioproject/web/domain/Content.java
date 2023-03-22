package Capstone.Bioproject.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public Content(User user,String content, Long disease) {
        this.user=user;
        this.content = content;
        this.disease = disease;
    }
}
