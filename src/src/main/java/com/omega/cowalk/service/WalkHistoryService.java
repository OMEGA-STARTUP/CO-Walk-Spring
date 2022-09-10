package com.omega.cowalk.service;

import com.omega.cowalk.domain.dto.WalkHistoryResponseDto;
import com.omega.cowalk.domain.entity.walkhistory.WalkHistory;
import com.omega.cowalk.repository.WalkHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalkHistoryService {

    private final WalkHistoryRepository walkHistoryRepository;

    @Async("walkHistoryThreadPoolTaskExecutor")
    @Transactional
    public void addWalkHistory(long userId){
        Optional<WalkHistory> findWalkHistory = walkHistoryRepository
                .findByWalkDateAndUserId(LocalDate.now(), userId);
        if(findWalkHistory.isEmpty()){
            WalkHistory walkHistory = WalkHistory.builder()
                    .walkDate(formatLocalDateNow())
                    .userId(userId)
                    .steps(1)
                    .build();
            walkHistoryRepository.save(walkHistory);
        } else {
            walkHistoryRepository.incrementStepsByWalkDateAndUserId(
                    findWalkHistory.get().getWalkDate(), findWalkHistory.get().getUserId());
        }
    }

    public List<WalkHistoryResponseDto> findByUserId(String month, long userId){
        LocalDate firstDayOfMonth = getFirstDayOfMonth(month);

        List<WalkHistory> walkHistories = walkHistoryRepository.findByUserId(firstDayOfMonth, LocalDate.now(), userId);

        List<WalkHistoryResponseDto> walkHistoryResponseDtos = new ArrayList<>();
        walkHistories.forEach(w ->
                walkHistoryResponseDtos.add(new ModelMapper().map(w, WalkHistoryResponseDto.class)));

        return walkHistoryResponseDtos;
    }

    private LocalDate formatLocalDateNow(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String text = date.format(formatter);

        return LocalDate.parse(text, formatter);
    }

    private LocalDate getFirstDayOfMonth(String month) {
        if(month.length() < 2){
            month = "0" + month;
        }
        return LocalDate.parse(String.format("%s-%s-01", LocalDate.now().getYear(), month));
    }
}
