package pl.shopmatelist.shopmatelist.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Recipes {
    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;
    @Column(name = "recipe_name")
    private String recipeName;
    @Column(name = "recipe_description")
    private String recipeDescription;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}