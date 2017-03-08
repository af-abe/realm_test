package appsfactory.de.realmtest;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by André on 07.03.2017.
 */

public class Model extends RealmObject {
    @PrimaryKey
    private Long id;
    private String bla;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBla() {
        return bla;
    }

    public void setBla(String bla) {
        this.bla = bla;
    }
}
