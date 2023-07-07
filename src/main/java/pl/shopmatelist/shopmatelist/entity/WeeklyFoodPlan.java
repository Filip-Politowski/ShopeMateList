package pl.shopmatelist.shopmatelist.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weekly_food_plan")
public class WeeklyFoodPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_food_plan_id")
    private Long weeklyFoodPlanId;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipes recipes;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @ManyToOne
    @JoinColumn(name = "food_plan_id")
    private FoodPlans foodPlan;


}
