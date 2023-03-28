package Capstone.Bioproject.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Table(name="diarytag")
@NoArgsConstructor
@Entity
@IdClass(DiarytagPK.class)
public class Diarytag {
    @Id
    @Column(name="diary")
    Long diary;
    @Id
    @Column(name = "tag")
    Long tag;

    @Builder
    public Diarytag(Long diary, Long tag) {
        this.diary=diary;
        this.tag=tag;
    }

    public void update(Long tag) {
        this.tag=tag;
    }
}
