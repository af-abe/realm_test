package appsfactory.de.realmtest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import appsfactory.de.realmtest.databinding.ActivityMainBinding;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Model> {

    public ObservableField<String> text = new ObservableField<>("bla");
    public ObservableField<String> db = new ObservableField<>("bla");
    private RandomString mRandomString = new RandomString(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setAc(this);
    }

    public void onUpdate(View v) {
        text.set(mRandomString.nextString());
        Realm realm = Realm.getDefaultInstance();
        final Model m = new Model();
        m.setBla(text.get());
        m.setId(1L);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // This will create a new object in Realm or throw an exception if the
                // object already exists (same primary key)
                // realm.copyToRealm(obj);

                // This will update an existing object with the same primary key
                // or create a new object if an object with no primary key = 42
                realm.copyToRealmOrUpdate(m);
            }
        });
    }

    public void onLoad(View v) {
        getSupportLoaderManager().restartLoader(0, null, this).forceLoad();
    }

    public void onLoadA(View v) {
        new AsyncTask<Void, Void, Model>() {
            @Override
            protected Model doInBackground(Void... params) {
                Realm realm = Realm.getDefaultInstance();
                Model result = realm.where(Model.class).equalTo("id", 1L).findFirst();
                if(result != null) {
                    return realm.copyFromRealm(result);
                } else {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Model data) {
                if (data != null) {
                    db.set(data.getBla());
                } else {
                    db.set("null");
                }
            }
        }.execute();
    }


    @Override
    public Loader<Model> onCreateLoader(int id, Bundle args) {
        return new appsfactory.de.realmtest.Loader(this);
    }

    @Override
    public void onLoadFinished(Loader<Model> loader, Model data) {
        if (data != null) {
            db.set(data.getBla());
        } else {
            db.set("null");
        }
    }

    @Override
    public void onLoaderReset(Loader<Model> loader) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Realm.init(newBase);
    }
}
