package Capstone.Bioproject.web.hospital.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class LocalDto {
    private Double lon;
    private Double lat;
    @Builder
    public LocalDto(Double lon, Double lat) {
        this.lon=lon;
        this.lat=lat;
    }
}
