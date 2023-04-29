package Capstone.Bioproject.web.repository;
import Capstone.Bioproject.web.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query(value = "SELECT *\n" +
            "FROM hospital\n" +
            "WHERE dgid_id_name LIKE %:type%\n" +
           " AND dgid_id_name NOT LIKE '%구강내과%'"+
            "AND dgid_id_name NOT LIKE '%한방내과%'"+
            "AND ST_Distance_Sphere(POINT(wgs84lon, wgs84lat), POINT(:lon, :lat )) <= 2000\n" +
            "ORDER BY ST_Distance_Sphere(POINT(wgs84lon, wgs84lat), POINT(:lon, :lat))",nativeQuery = true)
    List<Hospital> findByHospitalDistance(@Param("type") String type,
                                          @Param("lon") Double lon,
                                          @Param("lat") Double lat);

    @Query(value = "SELECT *\n" +
            "FROM hospital\n" +
            "WHERE dgid_id_name LIKE %:type%\n" +
            "AND dgid_id_name NOT LIKE '%한방내과%'"+
            "AND ST_Distance_Sphere(POINT(wgs84lon, wgs84lat), POINT(:lon, :lat )) <= 2000\n" +
            "ORDER BY ST_Distance_Sphere(POINT(wgs84lon, wgs84lat), POINT(:lon, :lat))",nativeQuery = true)
    List<Hospital> findByDentalDistance(@Param("type") String type,
                                          @Param("lon") Double lon,
                                          @Param("lat") Double lat);

}
