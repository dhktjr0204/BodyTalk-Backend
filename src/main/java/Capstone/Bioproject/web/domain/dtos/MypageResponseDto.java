package Capstone.Bioproject.web.domain.dtos;

import Capstone.Bioproject.web.domain.User;
import lombok.Data;

@Data
public class MypageResponseDto {
    private Long id;
    private String name;
    private String sex;
    private Long age;
    private String email;
    private String provider;

    public MypageResponseDto(User user){
        this.id=user.getId();
        this.name= user.getName();
        this.age=user.getAge();
        this.sex=user.getSex();
        this.email=user.getEmail();
        this.provider= user.getProvider();
    }
}
