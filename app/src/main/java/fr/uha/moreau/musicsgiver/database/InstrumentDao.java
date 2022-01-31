package fr.uha.moreau.musicsgiver.database;

import androidx.room.Dao;

import fr.uha.moreau.musicsgiver.model.Instrument;

@Dao
public interface InstrumentDao {
    long upsert(Instrument flûte);
}
