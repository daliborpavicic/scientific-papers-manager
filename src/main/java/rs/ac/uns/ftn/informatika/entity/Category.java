package rs.ac.uns.ftn.informatika.entity;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    Integer id;
    String name;

    public Category() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
