package fr.uha.moreau.musicsgiver.ui.groupes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.database.GroupeDao;
import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.model.MusicienGroupeAssociation;

public class GroupesListViewModel extends ViewModel {
    private GroupeDao groupeDao;
    private MediatorLiveData<List<Groupe>> groupes;
    private MediatorLiveData<List<MusicienGroupeAssociation>> mgas;


    public void setGroupeDao(GroupeDao groupeDao) {
        this.groupeDao = groupeDao;
        this.groupes = new MediatorLiveData<>();
        this.groupes.addSource(groupeDao.getAll(), groupes::setValue);
        this.mgas = new MediatorLiveData<>();
        this.mgas.addSource(groupeDao.getAllMgas(), mgas::setValue);
    }

    public LiveData<List<Groupe>> getGroupes() {
        return this.groupes;
    }

    public void deleteGroupe(Groupe g) {
        List<MusicienGroupeAssociation> mgas = groupeDao.getAllMgasByGID(g.getId());
        for (MusicienGroupeAssociation mga: mgas) {
            groupeDao.delete(mga);
        }
        groupeDao.delete(g);
    }

    public void addGroupe(Groupe g) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                groupeDao.upsert(g);
            }
        });
    }

    public long getLastIdGroupe() {
        return groupeDao.getLastId() != null ? groupeDao.getLastId().getId() : 1;
    }
}