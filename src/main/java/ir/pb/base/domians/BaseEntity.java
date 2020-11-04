package ir.pb.base.domians;



import javax.persistence.*;
import java.io.Serializable;
@MappedSuperclass
public class BaseEntity<PK extends Serializable> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
