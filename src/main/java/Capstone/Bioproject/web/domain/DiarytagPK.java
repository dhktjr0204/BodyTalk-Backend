package Capstone.Bioproject.web.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DiarytagPK implements Serializable {
    private Long diary;
    private Long tag;
}
