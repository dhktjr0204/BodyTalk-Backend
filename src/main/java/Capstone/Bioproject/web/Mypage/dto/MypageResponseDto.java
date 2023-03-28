package Capstone.Bioproject.web.Mypage.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MypageResponseDto {
    private Long id;
    private String name;
    private List<ContentResponseDto> content;

    @Builder
    public MypageResponseDto(Long id, String name, List<ContentResponseDto> content){
        this.id=id;
        this.name=name;
        this.content=content;
    }

}
