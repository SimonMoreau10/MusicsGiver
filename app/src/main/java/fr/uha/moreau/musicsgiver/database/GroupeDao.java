package fr.uha.moreau.musicsgiver.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienGroupeAssociation;

@Dao
public interface GroupeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long upsert(Groupe groupe);

    @Delete
    public void delete(Groupe groupe);

    @Query("SELECT * from groupes where id= :id")
    public Groupe getById(long id);

    @Query("SELECT * from groupes")
    public LiveData<List<Groupe>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMusicienGroupeAssociation(MusicienGroupeAssociation musicienGroupeAssociation);

    @Query("Select * from musicienGroupeAssociations")
    public LiveData<List<MusicienGroupeAssociation>> getAllMgas();

    @Delete
    public void delete(MusicienGroupeAssociation mga);

    @Query("DELETE FROM GROUPES")
    public void deleteAllGroupes();

    @Query("DELETE FROM MUSICIENGROUPEASSOCIATIONS")
    public void deleteAllMgas();

    @Query("Select * from musicienGroupeAssociations where gid= :id")
    public List<MusicienGroupeAssociation> getAllMgasByGID(long id);

    @Query("SELECT * FROM groupes ORDER BY ID DESC LIMIT 1")
    public Groupe getLastId();
}
