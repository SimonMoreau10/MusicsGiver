package fr.uha.moreau.musicsgiver.ui.instruments;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.database.GroupeDao;
import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.model.Instrument;

public class InstrumentsListViewModel extends ViewModel {
    private InstrumentDao instrumentDao;
    private MusicienDao musicienDao;
    private GroupeDao groupeDao;
    private MediatorLiveData<List<Instrument>> instruments;

    public void setInstrumentDao(InstrumentDao instrumentDao) {
        this.instrumentDao = instrumentDao;
        this.instruments = new MediatorLiveData<>();
        this.instruments.addSource(instrumentDao.getAll(), instruments::setValue);
    }

    public void setMusicienDao(MusicienDao musicienDao) {
        this.musicienDao = musicienDao;
    }

    public void setGroupeDao(GroupeDao groupeDao) {
        this.groupeDao = groupeDao;
    }

    public LiveData<List<Instrument>> getInstruments() {
        return instruments;
    }

    public void save(Instrument i) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                instrumentDao.upsert(i);
            }
        });
    }

    public void deleteInstrument(Instrument i) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                instrumentDao.delete(i);
            }
        });
    }

    public void deleteAll() {
        deleteAllFromGroupe();
        deleteAllFromMusicien();
        deleteAllFromInstrument();
    }

    private void deleteAllFromInstrument() {
        instrumentDao.deleteAllInstruments();
    }

    private void deleteAllFromMusicien() {
        musicienDao.deleteAllMnfas();
        musicienDao.deleteAllMusiciens();
    }

    private void deleteAllFromGroupe() {
        groupeDao.deleteAllMgas();
        groupeDao.deleteAllGroupes();
    }
}
