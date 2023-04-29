package Capstone.Bioproject.web.hospital;
import Capstone.Bioproject.web.domain.Hospital;
import Capstone.Bioproject.web.hospital.dto.HospitalInfoDto;
import Capstone.Bioproject.web.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    //최근 진료 기록 보기
    @Transactional
    public List<Hospital> getMyContents(HospitalInfoDto hospitalInfoDto) {
        if(hospitalInfoDto.getType().equals("치과")){
            return hospitalRepository.findByDentalDistance(hospitalInfoDto.getType(), hospitalInfoDto.getLon(), hospitalInfoDto.getLat());
        }else {
            return hospitalRepository.findByHospitalDistance(hospitalInfoDto.getType(), hospitalInfoDto.getLon(), hospitalInfoDto.getLat());
        }
    }
}
