package Capstone.Bioproject.web.Mypage.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyInfoUpdateRequestDto {
    private String name;
    private String sex;
    private Long age;

    @Builder
    public MyInfoUpdateRequestDto( String name, String sex, Long age){
        this.name=name;
        this.sex=sex;
        this.age=age;
    }
}