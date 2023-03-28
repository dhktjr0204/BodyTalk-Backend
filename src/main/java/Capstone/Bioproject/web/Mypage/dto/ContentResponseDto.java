package Capstone.Bioproject.web.Mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentResponseDto {
    Long id;
    String content;
    String disease;

    @Builder
    public ContentResponseDto(Long id, String content, String disease){
        this.id=id;
        this.content=content;
        this.disease=disease;
    }
}
