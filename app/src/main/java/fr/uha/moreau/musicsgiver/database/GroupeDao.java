package fr.uha.moreau.musicsgiver.database;

import androidx.room.Dao;

import fr.uha.moreau.musicsgiver.model.Groupe;

@Dao
public interface GroupeDao {

    long upsert(Groupe bestGroupEver);
}
