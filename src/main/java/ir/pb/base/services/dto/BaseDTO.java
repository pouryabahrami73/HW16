package ir.pb.base.services.dto;

import java.io.Serializable;

public class BaseDTO<PK extends Serializable> implements Serializable {

    private PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
