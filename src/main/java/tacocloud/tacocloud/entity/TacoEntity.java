package tacocloud.tacocloud.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "taco")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TacoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST)
    private List<IngredientEntity> ingredients;

    @Column(nullable = false)
    private String size;
}
