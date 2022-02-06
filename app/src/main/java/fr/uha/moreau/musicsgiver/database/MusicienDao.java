package fr.uha.moreau.musicsgiver.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;

@Dao
public interface MusicienDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long upsert(Musicien musicien);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMusicienNiveauFormationAssociation(MusicienNiveauFormationAssociation musicienNiveauFormationAssociation);

    @Query("Select * from musicien")
    public LiveData<List<Musicien>> getAll();
}
