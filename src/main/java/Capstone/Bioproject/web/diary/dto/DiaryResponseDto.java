package Capstone.Bioproject.web.diary.dto;

import Capstone.Bioproject.web.domain.Diary;
import Capstone.Bioproject.web.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String content;
    private LocalDate date;
    List<String> tag;
}
