package pl.shopmatelist.shopmatelist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shopmatelist.shopmatelist.dto.FoodPlansDTO;
import pl.shopmatelist.shopmatelist.entity.FoodPlans;
import pl.shopmatelist.shopmatelist.mapper.FoodPlansMapper;
import pl.shopmatelist.shopmatelist.repository.FoodPlansRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FoodPlansService {

    private final FoodPlansRepository foodPlansRepository;
    private final FoodPlansMapper foodPlansMapper;

    public FoodPlansDTO findById(Long id){
        Optional<FoodPlans> foodPlan = foodPlansRepository.findById(id);
        if(foodPlan.isPresent()){
            FoodPlans foundFoodPlan = foodPlan.get();
            return foodPlansMapper.toDto(foundFoodPlan);
        }
        throw new NoSuchElementException();
    }

    public List<FoodPlansDTO> findAll(){
        List<FoodPlans> foodPlans = foodPlansRepository.findAll();
        return foodPlansMapper.toDtoList(foodPlans);
        }

        public FoodPlansDTO save (FoodPlansDTO foodPlansDTO){
        FoodPlans foodPlan = foodPlansMapper.toEntity(foodPlansDTO);
        FoodPlans savedFoodPlan = foodPlansRepository.save(foodPlan);
        return foodPlansMapper.toDto(savedFoodPlan);
        }

        public void delete(Long id){
        foodPlansRepository.deleteById(id);
        }

        public FoodPlansDTO update(FoodPlansDTO foodPlansDTO){
        FoodPlans foodPlan = foodPlansMapper.toEntity(foodPlansDTO);
        FoodPlans updatedFoodPlan = foodPlansRepository.save(foodPlan);
        return foodPlansMapper.toDto(updatedFoodPlan);
        }
    }

