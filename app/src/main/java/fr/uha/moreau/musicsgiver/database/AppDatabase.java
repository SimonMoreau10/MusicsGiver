package fr.uha.moreau.musicsgiver.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienGroupeAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;


// @TypeConverters({DatabaseTypeConverters.class})
@Database(entities = {
        Musicien.class,
        MusicienGroupeAssociation.class,
        Groupe.class,
        MusicienNiveauFormationAssociation.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;
    private static MutableLiveData<AppDatabase> ready = new MutableLiveData<>(null);

    static public void create(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "musicsGiver.db").build();
            ready.postValue(instance);
        }
    }

    public static LiveData<AppDatabase> isReady() {
        return ready;
    }

    static public AppDatabase get () {
        return instance;
    }

    public abstract GroupeDao getGroupeDao ();
    public abstract InstrumentDao getInstrumentDao ();
    public abstract MusicienDao getMusicienDao ();

}
