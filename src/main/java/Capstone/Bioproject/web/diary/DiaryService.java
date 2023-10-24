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
import java.util.stream.Collectors;

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
        List<Diarytag> diarytags=Arrays.stream(tags)
                .map(tag->{
                    Long tagId=tagRepository.findBySymptom(tag).getId();
                    return new Diarytag(diaryId,tagId);
                }).collect(Collectors.toList());
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
        String[] tags=diaryRequestDto.getTags().split(",");
        List<Diarytag> diarytags = Arrays.stream(tags)
                .map(tag -> {
                    Long tagId = tagRepository.findBySymptom(tag).getId();
                    //수정할 다이어리
                    List<Diarytag> byDiary = diaryTagRepository.findByDiary(id);
                    //기존에 저장되어있던 태그 모드 삭제
                    byDiary.forEach(diarytag -> diaryTagRepository.delete(diarytag));
                    return new Diarytag(id, tagId);
                })
                .collect(Collectors.toList());
        diaryTagRepository.saveAll(diarytags);
        return mypageService.makeResponse("update");
    }

    @Transactional
    public DiaryResponseDto getDiary(Long id){
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다.: " + id));

        List<Diarytag> diarytags = diaryTagRepository.findByDiary(id);

        List<String> tags = diarytags.stream()
                .map(diarytag -> tagRepository.findById(diarytag.getTag())
                        .orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다.: " + id))
                        .getSymptom())
                .collect(Collectors.toList());

        DiaryResponseDto result = DiaryResponseDto.builder()
                .id(id)
                .content(diary.getContent())
                .date(diary.getDate())
                .tag(tags).build();

        return  result;
    }

    @Transactional
    public List<DiaryResponseDto> getAllOfDiary(User user){
        List<Diary> diarys = diaryRepository.findByUser(user);

        List<DiaryResponseDto> result = diarys.stream()
                .map(diary -> {
                    List<String> tags = diaryTagRepository.findByDiary(diary.getId()).stream()
                            .map(diarytag -> tagRepository.findById(diarytag.getTag())
                                    .orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다.: "))
                                    .getSymptom())
                            .collect(Collectors.toList());
                    return DiaryResponseDto.builder()
                            .id(diary.getId())
                            .content(diary.getContent())
                            .date(diary.getDate())
                            .tag(tags).build();
                })
                .collect(Collectors.toList());

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

        List<ChartResponseDto> Symptomresult = SymtomTop3.stream()
                .map(symptom -> {
                    List<LocalDate> dates = diaryRepository.findSymtomDate(user.getId(),
                                    chartRequestDto.getStart(), chartRequestDto.getEnd(), symptom.getSymptom())
                            .stream()
                            .map(DateInterface::getDate)
                            .collect(Collectors.toList());
                    return ChartResponseDto.builder()
                            .symtomRank(symptom.getSymptom())
                            .dates(dates)
                            .build();
                })
                .collect(Collectors.toList());

        List<TypeInterface> types=diaryRepository.findType(user.getId(),chartRequestDto.getStart(), chartRequestDto.getEnd());
        TypeResponseDto result = TypeResponseDto.builder().symptomInfo(Symptomresult).typeInfo(types).build();
        return result;
    }
}
