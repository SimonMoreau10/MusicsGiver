package fr.uha.moreau.musicsgiver.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Instrument;

@Dao
public interface InstrumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long upsert(Instrument instrument);

    @Query("Select * from instruments where id = :id")
    public Instrument getById(long id);

    @Query("Select * from instruments")
    public LiveData<List<Instrument>> getAll();

    @Query("Select * from instruments where classe = :classeDInstrument")
    public LiveData<List<Instrument>> getAllInstrumentsByClasse(ClasseDInstrument classeDInstrument);

    @Delete
    public void delete(Instrument i);

    @Query("DELETE FROM INSTRUMENTS")
    public void deleteAllInstruments();
}
