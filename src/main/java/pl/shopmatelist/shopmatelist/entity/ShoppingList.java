package pl.shopmatelist.shopmatelist.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_list_id")
    private Long shoppingListId;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @Column(name = "shopping_date")
    private LocalDate shoppingDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "owner")
    private Boolean owner;




}
