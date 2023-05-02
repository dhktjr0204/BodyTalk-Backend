package Capstone.Bioproject.web.hospital;

import Capstone.Bioproject.web.domain.Hospital;
import Capstone.Bioproject.web.hospital.dto.HospitalInfoDto;
import Capstone.Bioproject.web.hospital.dto.LocalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HospitalController {
    private final HospitalService hospitalService;
    @PostMapping("/api/hospital")
    public List<Hospital> getNearByHospital(@RequestBody HospitalInfoDto hospitalInfoDto){
        return hospitalService.getMyContents(hospitalInfoDto);
    }

    @PostMapping("/api/hospital/coordi")
    public LocalDto getNearByHospital(@RequestParam(value = "local") String local){
        return hospitalService.getMyCoordi(local);
    }
}
