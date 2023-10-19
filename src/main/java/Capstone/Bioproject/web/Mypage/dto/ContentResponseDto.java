package Capstone.Bioproject.web.Mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponseDto {
    Long id;
    String content;
    String disease;
    LocalDate date;
}
