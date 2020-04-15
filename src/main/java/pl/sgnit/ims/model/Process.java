package pl.sgnit.ims.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "processes")
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String owner;

    private String state;

    @NotBlank
    @Size(max=10)
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeAndName() {
        return code+" - "+name;
    }

    @PrePersist
    public void prePersist() {
        state = "W przygotowaniu";
        code = code.toUpperCase();
    }

    @PreUpdate
    public void preUpdate() {
        code = code.toUpperCase();
    }
}
