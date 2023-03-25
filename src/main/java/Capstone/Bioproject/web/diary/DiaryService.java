package Capstone.Bioproject.web.diary;

import Capstone.Bioproject.web.diary.dto.ChartRequestDto;
import Capstone.Bioproject.web.diary.dto.ChartResponseDto;
import Capstone.Bioproject.web.diary.dto.DiaryRequestDto;
import Capstone.Bioproject.web.diary.dto.TypeResponseDto;
import Capstone.Bioproject.web.domain.*;
import Capstone.Bioproject.web.repository.DiaryRepository;
import Capstone.Bioproject.web.repository.DiarytagRepository;
import Capstone.Bioproject.web.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public LocalDate StringtoDate(String date){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime= LocalDate.parse(date,formatter);
        return dateTime;
    }

    @Transactional
    public ResponseEntity<Map<String, Boolean>> save(User user, DiaryRequestDto diaryRequestDto){
        LocalDate dateTime= StringtoDate(diaryRequestDto.getDate());
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

    @Transactional
    public TypeResponseDto  sendGraph(User user, ChartRequestDto chartRequestDto){
        //3달동안 가장 많이 나타나는 증상 3가지 뽑기
        List<SymptomRankInterface> SymtomTop3
                =diaryRepository.findSymptomRank(user.getId(), chartRequestDto.getStart(), chartRequestDto.getEnd());

        List<ChartResponseDto> Symptomresult=new ArrayList<>();
        //증상 별 다이어리 날짜 뽑기
        for (SymptomRankInterface i: SymtomTop3){
            List<LocalDate> dates=new ArrayList<>();
            SymptomRankInterface symptom = i;
            List<DateInterface> symptomDate = diaryRepository.findSymtomDate(user.getId(),
                    chartRequestDto.getStart(), chartRequestDto.getEnd(), symptom.getSymptom());
            for (DateInterface date: symptomDate){
                dates.add(date.getDate());
            }
            Symptomresult.add(ChartResponseDto.builder().symtomRank(symptom.getSymptom()).dates(dates).build());
        }
        List<TypeInterface> types=diaryRepository.findType(user.getId(),chartRequestDto.getStart(), chartRequestDto.getEnd());
        TypeResponseDto result = TypeResponseDto.builder().symptomInfo(Symptomresult).typeInfo(types).build();
        return result;
    }
}
