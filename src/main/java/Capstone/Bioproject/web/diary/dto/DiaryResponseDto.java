package Capstone.Bioproject.web.diary.dto;

import Capstone.Bioproject.web.domain.Diary;
import Capstone.Bioproject.web.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String content;
    private LocalDateTime date;
    List<String> symptom;

    @Builder
    public static DiaryResponseDto of(Long id, String content, List<String> symptom){
        return DiaryResponseDto.builder()
                .id(id).content(content).symptom(symptom).build();
    }
}
