package Capstone.Bioproject.web.diary.dto;

import Capstone.Bioproject.web.domain.DateInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChartResponseDto {
    private String symtomRank;
    private List<LocalDate> dates;
}