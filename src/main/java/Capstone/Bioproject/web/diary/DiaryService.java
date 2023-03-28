package Capstone.Bioproject.web.diary;

import Capstone.Bioproject.web.Mypage.MypageService;
import Capstone.Bioproject.web.diary.dto.*;
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
import java.util.*;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final TagRepository tagRepository;
    private final DiarytagRepository diaryTagRepository;
    private final MypageService mypageService;

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
        return mypageService.makeResponse("save");
    }

    @Transactional
    public ResponseEntity<Map<String,Boolean>> update(Long id, DiaryRequestDto diaryRequestDto){
        LocalDate dateTime= StringtoDate(diaryRequestDto.getDate());
        //다이어리 업데이트
        Diary diary = diaryRepository.getById(id);
        diary.update(diaryRequestDto.getContent(),dateTime);
        //태그 업데이트
        List<Diarytag> diarytags=new ArrayList<>();
        String[] tags=diaryRequestDto.getTags().split(",");
        for (String tag : tags){
            Long tagId=tagRepository.findBySymptom(tag).getId();
            List<Diarytag> byDiary = diaryTagRepository.findByDiary(id);
            //기존에 저장되어있던 태그 모두 삭제
            for(Diarytag diarytag: byDiary){
                diaryTagRepository.delete(diarytag);
            }
            //태그 다시 저장
            Diarytag diaryTag= Diarytag.builder().diary(id).tag(tagId).build();
            diarytags.add(diaryTag);
        }
        diaryTagRepository.saveAll(diarytags);
        return mypageService.makeResponse("update");
    }

    @Transactional
    public DiaryResponseDto getDiary(Long id){
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다.: " + id));

        List<Diarytag> diarytags = diaryTagRepository.findByDiary(id);
        List<String> tags=new ArrayList<>();
        for (Diarytag tag: diarytags){
            Tag tagInfo = tagRepository.findById(tag.getTag())
                    .orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다.: " + id));
            tags.add(tagInfo.getSymptom());
        }

        DiaryResponseDto result = DiaryResponseDto.builder()
                .id(id)
                .content(diary.getContent())
                .date(diary.getDate())
                .tag(tags).build();

        return  result;
    }

    @Transactional
    public List<DiaryResponseDto> getAllOfDiary(User user){
        List<Diary> Diarys = diaryRepository.findByUser(user);

        List<DiaryResponseDto> result=new ArrayList<>();
        for(Diary diary: Diarys) {
            List<String> tags=new ArrayList<>();

            List<Diarytag> diarytags = diaryTagRepository.findByDiary(diary.getId());
            for (Diarytag tag : diarytags) {
                Tag tagInfo = tagRepository.findById(tag.getTag())
                        .orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다.: "));
                tags.add(tagInfo.getSymptom());
            }
            DiaryResponseDto build = DiaryResponseDto.builder()
                    .id(diary.getId())
                    .content(diary.getContent())
                    .date(diary.getDate())
                    .tag(tags).build();

            result.add(build);
        }
        return result;
    }

    @Transactional
    public ResponseEntity<Map<String,Boolean>> delete(Long id){
        Diary diary= diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다: "+id));
        diaryRepository.delete(diary);
        return mypageService.makeResponse("delete");
    }

    @Transactional
    public TypeResponseDto sendGraph(User user, ChartRequestDto chartRequestDto){
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
