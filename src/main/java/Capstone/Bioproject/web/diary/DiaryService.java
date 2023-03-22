package Capstone.Bioproject.web.diary;

import Capstone.Bioproject.web.diary.dto.DiaryRequestDto;
import Capstone.Bioproject.web.domain.Diary;
import Capstone.Bioproject.web.domain.Diarytag;
import Capstone.Bioproject.web.domain.User;
import Capstone.Bioproject.web.repository.DiaryRepository;
import Capstone.Bioproject.web.repository.DiarytagRepository;
import Capstone.Bioproject.web.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final TagRepository tagRepository;
    private final DiarytagRepository diaryTagRepository;

    @Transactional
    public ResponseEntity<Map<String, Boolean>> save(User user, DiaryRequestDto diaryRequestDto){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime= LocalDate.parse(diaryRequestDto.getDate(),formatter);
        //메모 저장, id 빼오기
        Diary diary = Diary.builder()
                .user(user).content(diaryRequestDto.getContent())
                .date(dateTime).build();
        Long diaryId= diaryRepository.save(diary).getId();

        String[] tags=diaryRequestDto.getTags().split(",");
        //태그 저장
        List<Diarytag> diarytags=new ArrayList<>();
        for (String tag : tags){
            Long tagId=tagRepository.findBySymptom(tag).getId();
            Diarytag diaryTag= Diarytag.builder().diary(diaryId).tag(tagId).build();
            diarytags.add(diaryTag);
        }
        diaryTagRepository.saveAll(diarytags);


        Map<String, Boolean> response = new HashMap<>();
        response.put("save", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }
}
