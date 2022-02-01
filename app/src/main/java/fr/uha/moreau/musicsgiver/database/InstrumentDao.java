package fr.uha.moreau.musicsgiver.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import fr.uha.moreau.musicsgiver.model.Instrument;

@Dao
public interface InstrumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long upsert(Instrument instrument);
}
