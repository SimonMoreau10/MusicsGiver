package fr.uha.moreau.musicsgiver.ui.groupes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;

public class MusicienParFormationViewModel extends ViewModel {
    private MusicienDao musicienDao;
    private InstrumentDao instrumentDao;
    private MediatorLiveData<List<Instrument>> instruments;
    private MediatorLiveData<List<MusicienNiveauFormationAssociation>> mnfas;

    public void setMusicienDao(MusicienDao musicienDao) {
        this.musicienDao = musicienDao;
        this.mnfas = new MediatorLiveData<>();
        this.mnfas.addSource(musicienDao.getAllMnfas(), mnfas::setValue);
    }

    public void setInstrumentDao(InstrumentDao instrumentDao) {
        this.instrumentDao = instrumentDao;
        this.instruments = new MediatorLiveData<>();
        this.instruments.addSource(instrumentDao.getAll(), instruments::setValue);
    }

    public MusicienDao getMusicienDao() {
        return musicienDao;
    }

    public InstrumentDao getInstrumentDao() {
        return instrumentDao;
    }

    public LiveData<List<MusicienNiveauFormationAssociation>> getMusicienNiveauFormationAssociation() {
        return this.mnfas;
    }

    public LiveData<List<Musicien>> getMusiciensByFormation(Formation formation) {
        return musicienDao.getAllMusiciensByFormation(formation);
    }

    public LiveData<List<MusicienNiveauFormationAssociation>> getMusicienNiveauFormationAssociationByFormation(Formation formation) {
        return musicienDao.getAllMnfasByFormation(formation);
    }
}