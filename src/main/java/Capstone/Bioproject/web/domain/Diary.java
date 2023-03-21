package Capstone.Bioproject.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String content;
    private String disease;

    @Builder
    public Diary(User user,String content, String disease, String diseaseinfo) {
        this.user=user;
        this.content = content;
        this.disease = disease;
    }
}
