package appsfactory.de.realmtest;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import io.realm.Realm;

/**
 * Created by André on 07.03.2017.
 */

public class Loader extends AsyncTaskLoader<Model> {
    public Loader(Context context) {
        super(context);
    }

    @Override
    public Model loadInBackground() {
        Realm realm = Realm.getDefaultInstance();
        Model result = realm.where(Model.class).equalTo("id", 1).findFirst();
        if(result != null) {
            return realm.copyFromRealm(result);
        } else {
            return null;
        }
    }
}
