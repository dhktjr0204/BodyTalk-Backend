package Capstone.Bioproject.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dgidIdName;
    private String dutyAddr;
    private String dutyName;
    private String dutyTel1;
    private String dutyTel3;
    private String dutyTime1c;
    private String dutyTime1s;
    private String dutyTime2c;
    private String dutyTime2s;
    private String dutyTime3c;
    private String dutyTime3s;
    private String dutyTime4c;
    private String dutyTime4s;
    private String dutyTime5c;
    private String dutyTime5s;
    private String dutyTime6c;
    private String dutyTime6s;
    private Double wgs84Lat;
    private Double wgs84Lon;
}