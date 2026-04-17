package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "Catgories")

public class Category {
    @Id
    private Long id;
}
