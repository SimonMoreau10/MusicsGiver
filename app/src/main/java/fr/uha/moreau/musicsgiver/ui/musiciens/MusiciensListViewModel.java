package fr.uha.moreau.musicsgiver.ui.musiciens;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;

public class MusiciensListViewModel extends ViewModel {
    private MusicienDao musicienDao;
    private MediatorLiveData<List<Musicien>> musiciens;

    public void addMusicien(Musicien m) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.get().getMusicienDao().upsert(m);}
        });
    }


    public void addAssociation(MusicienNiveauFormationAssociation mnfa) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.get().getMusicienDao().addMusicienNiveauFormationAssociation(mnfa);
            }
        });
    }

    public void setMusicienDao(MusicienDao musicienDao) {
        this.musicienDao = musicienDao;
        this.musiciens = new MediatorLiveData<>();
        this.musiciens.addSource(musicienDao.getAll(), musiciens::setValue);

    }

    public LiveData<List<Musicien>> getMusiciens() {
        return this.musiciens;
    }
}