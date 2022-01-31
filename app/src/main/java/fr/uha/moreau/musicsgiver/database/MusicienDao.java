package fr.uha.moreau.musicsgiver.database;

import androidx.room.Dao;

import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;

@Dao
public interface MusicienDao {
    long upsert(Musicien musicien);

    void addMusicienNiveauFormationAssociation(MusicienNiveauFormationAssociation musicienNiveauFormationAssociation);
}
