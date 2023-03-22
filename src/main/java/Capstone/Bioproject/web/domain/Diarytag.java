package Capstone.Bioproject.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
