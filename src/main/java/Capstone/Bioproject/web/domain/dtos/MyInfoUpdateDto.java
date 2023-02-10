package Capstone.Bioproject.web.domain.dtos;

import Capstone.Bioproject.web.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyInfoUpdateDto {
    private Long id;
    private String name;
    private String sex;
    private Long age;

    @Builder
    public MyInfoUpdateDto(Long id,String name, String sex, Long age){
        this.id=id;
        this.name=name;
        this.sex=sex;
        this.age=age;
    }
}