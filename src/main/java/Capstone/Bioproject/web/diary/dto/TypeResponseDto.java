package Capstone.Bioproject.web.diary.dto;

import Capstone.Bioproject.web.domain.TypeInterface;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class TypeResponseDto {
    private List<ChartResponseDto> symptomInfo;
    private List<TypeInterface> typeInfo;

    @Builder
    public TypeResponseDto (List<ChartResponseDto> symptomInfo,List<TypeInterface> typeInfo) {
        this.symptomInfo=symptomInfo;
        this.typeInfo=typeInfo;
    }
}
