package Capstone.Bioproject.web.diary.dto;

import lombok.Getter;

@Getter
public class DiaryRequestDto {
    String content;
    String tags;
    Long year;
    Long month;
    Long day;
    String date;
}
