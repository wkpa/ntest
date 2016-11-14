package server.domain;

import net.customware.gwt.dispatch.shared.Result;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "todo")
public class ToDo implements Serializable, Result {

    private Integer id;
    private String name;
    private String description;

    public ToDo(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ToDo() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq")
    @SequenceGenerator(name = "todo_seq", sequenceName = "todo_seq", allocationSize=1)
    @Column(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }


//    @NotBlank
//    @Size(min = 3, max = 50)
    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


//    @NotBlank
//    @Size(min = 3, max = 250)
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
