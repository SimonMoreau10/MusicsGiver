package fr.uha.moreau.musicsgiver.ui.groupes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.uha.moreau.musicsgiver.database.GroupeDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienGroupeAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;

public class GroupeViewModel extends ViewModel {
    private GroupeDao groupeDao;
    private MediatorLiveData<List<Musicien>> musiciens;
    private MusicienDao musicienDao;
    private MediatorLiveData<List<MusicienGroupeAssociation>> mgas;

    public void setGroupeDao(GroupeDao groupeDao) {
        this.groupeDao = groupeDao;
    }


   /* private LiveData<List<Musicien>> setMusiciens(Long id) {
        System.out.println(groupeDao);
        LiveData<List<Musicien>> musiciens = groupeDao.getAllMusiciensByGID(id);
        return musiciens;
    } */

    public GroupeDao getGroupeDao() {
        return groupeDao;
    }

    public LiveData<List<Musicien>> getMusiciens() {
        return musiciens;
    }

    public MusicienDao getMusicienDao() {
        return musicienDao;
    }

    public void setMusicienDao(MusicienDao musicienDao) {
        this.musicienDao = musicienDao;
        this.mgas = new MediatorLiveData<>();
        this.mgas.addSource(musicienDao.getAllMgas(), mgas::setValue);
    }


    public LiveData<List<MusicienGroupeAssociation>> getMusicienGroupeAssociationByGID(long groupeId) {
        return groupeDao.getAllMgasByGID(groupeId);
    }

    public void delete(MusicienGroupeAssociation musicienGroupeAssociation) {
        groupeDao.delete(musicienGroupeAssociation);
    }
}