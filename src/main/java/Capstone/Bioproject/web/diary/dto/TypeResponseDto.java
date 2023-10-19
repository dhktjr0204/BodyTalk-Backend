package Capstone.Bioproject.web.diary.dto;

import Capstone.Bioproject.web.domain.TypeInterface;
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
public class TypeResponseDto {
    private List<ChartResponseDto> symptomInfo;
    private List<TypeInterface> typeInfo;
}
