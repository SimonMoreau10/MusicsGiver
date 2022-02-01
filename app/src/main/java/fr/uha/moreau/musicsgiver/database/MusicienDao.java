package fr.uha.moreau.musicsgiver.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;

@Dao
public interface MusicienDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long upsert(Musicien musicien);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMusicienNiveauFormationAssociation(MusicienNiveauFormationAssociation musicienNiveauFormationAssociation);
}
