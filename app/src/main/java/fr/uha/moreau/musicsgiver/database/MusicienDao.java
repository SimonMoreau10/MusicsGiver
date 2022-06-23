package fr.uha.moreau.musicsgiver.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienGroupeAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;

@Dao
public interface MusicienDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long upsert(Musicien musicien);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMusicienNiveauFormationAssociation(MusicienNiveauFormationAssociation musicienNiveauFormationAssociation);

    @Query("Select * from musicien")
    public LiveData<List<Musicien>> getAllMusiciens();

    @Query("Select * from musicienNiveauFormationAssociation")
    public LiveData<List<MusicienNiveauFormationAssociation>> getAllMnfas();

    @Query("Select * from musicien where id= :id")
    public Musicien getById(long id);

    @Query("Select * from musicienNiveauFormationAssociation where mid= :mid")
    public MusicienNiveauFormationAssociation getMnfaByMid(long mid);

    @Query("SELECT * FROM musicien ORDER BY ID DESC LIMIT 1")
    public Musicien getLastId();

    @Delete
    public void delete(Musicien musicien);

    @Delete
    public void delete(MusicienNiveauFormationAssociation mnfa);

    @Query("DELETE FROM musicienNiveauFormationAssociation")
    public void deleteAllMnfas();

    @Query("Delete from musicien")
    public void deleteAllMusiciens();

    @Query("Select m.id, m.firstName, m.lastName from musicien m inner join musicienNiveauFormationAssociation mnfa on m.id = mnfa.mid where mnfa.formation = :formation")
    LiveData<List<Musicien>> getAllMusiciensByFormation(Formation formation);

    @Query("Select * from musicienNiveauFormationAssociation mnfa where mnfa.formation = :formation")
    LiveData<List<MusicienNiveauFormationAssociation>> getAllMnfasByFormation(Formation formation);

    @Query("Select * from musicienGroupeAssociations")
    LiveData<List<MusicienGroupeAssociation>> getAllMgas();
}
