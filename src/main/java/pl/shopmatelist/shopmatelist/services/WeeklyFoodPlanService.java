package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.WeeklyFoodPlanDTO;

import pl.shopmatelist.shopmatelist.entity.WeeklyFoodPlan;
import pl.shopmatelist.shopmatelist.mapper.WeeklyFoodPlanMapper;
import pl.shopmatelist.shopmatelist.repository.WeeklyFoodPlanRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeeklyFoodPlanService {

    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;
    private final WeeklyFoodPlanMapper weeklyFoodPlanMapper;

    public WeeklyFoodPlanDTO findById(Long id) {
        Optional<WeeklyFoodPlan> optionalWeeklyFoodPlan = weeklyFoodPlanRepository.findById(id);
        if (optionalWeeklyFoodPlan.isPresent()) {
            WeeklyFoodPlan foundWeeklyFoodPlan = optionalWeeklyFoodPlan.get();
            return weeklyFoodPlanMapper.toDto(foundWeeklyFoodPlan);
        }
        throw new NoSuchElementException();
    }

    public List<WeeklyFoodPlanDTO> findAll() {
        List<WeeklyFoodPlan> WeeklyFoodPlans = weeklyFoodPlanRepository.findAll();
        return weeklyFoodPlanMapper.toDtoList(WeeklyFoodPlans);
    }

    public List<WeeklyFoodPlanDTO> findAllByFoodPlanId(Long id){
        List<WeeklyFoodPlan> weeklyFoodPlans = weeklyFoodPlanRepository.findAllByFoodPlanId(id);
        return weeklyFoodPlanMapper.toDtoList(weeklyFoodPlans);
    }


    public WeeklyFoodPlanDTO save(WeeklyFoodPlanDTO weeklyFoodPlanDTO) {
        WeeklyFoodPlan WeeklyFoodPlan = weeklyFoodPlanMapper.toEntity(weeklyFoodPlanDTO);
        WeeklyFoodPlan savedWeeklyFoodPlan = weeklyFoodPlanRepository.save(WeeklyFoodPlan);
        return weeklyFoodPlanMapper.toDto(savedWeeklyFoodPlan);
    }

    public void deleteById(Long id) {
        weeklyFoodPlanRepository.deleteById(id);
    }

    public WeeklyFoodPlanDTO update(WeeklyFoodPlanDTO weeklyFoodPlanDTO) {
        WeeklyFoodPlan WeeklyFoodPlan = weeklyFoodPlanMapper.toEntity(weeklyFoodPlanDTO);
        WeeklyFoodPlan updatedWeeklyFoodPlan = weeklyFoodPlanRepository.save(WeeklyFoodPlan);
        return weeklyFoodPlanMapper.toDto(updatedWeeklyFoodPlan);

    }



}
