package Capstone.Bioproject.web.diary.dto;

import Capstone.Bioproject.web.domain.DateInterface;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChartResponseDto {
    private String symtomRank;
    private List<LocalDate> dates;

    @Builder
    public ChartResponseDto (String symtomRank,List<LocalDate> dates) {
        this.symtomRank=symtomRank;
        this.dates=dates;
    }
}