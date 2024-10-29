package tn.esprit.tpfoyer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Foyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idFoyer;

    String nomFoyer;
    long capaciteFoyer;

    @OneToOne(mappedBy = "foyer")
    @ToString.Exclude
    @JsonIgnore
    Universite universite;

    @OneToMany(mappedBy = "foyer")
            @JsonIgnore
            @ToString.Exclude
    Set<Bloc> blocs;

    public Foyer(long l, String nomFoyer, long capaciteFoyer) {
        this.idFoyer = l;
        this.nomFoyer = nomFoyer;
        this.capaciteFoyer = capaciteFoyer;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foyer foyer = (Foyer) o;
        return capaciteFoyer == foyer.capaciteFoyer && Objects.equals(nomFoyer, foyer.nomFoyer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomFoyer, capaciteFoyer);
    }
}


