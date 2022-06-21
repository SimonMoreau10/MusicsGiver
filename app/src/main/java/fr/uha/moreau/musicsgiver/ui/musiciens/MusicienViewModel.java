package fr.uha.moreau.musicsgiver.ui.musiciens;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienWithDetails;
import fr.uha.moreau.musicsgiver.model.Niveau;

public class MusicienViewModel extends ViewModel {

    private MusicienDao musicienDao;
    private InstrumentDao instrumentDao;
    private MusicienWithDetails musicien;
    private String prenom;
    private String nom;
    private ClasseDInstrument classeDInstrument;
    private Instrument instrument;
    private Niveau niveau;
    private Formation formation;

    public MusicienDao getMusicienDao() {
        return musicienDao;
    }

    public void addMusicien(Musicien m) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.get().getMusicienDao().upsert(m);}
        });
    }

    public void setMusicien(MusicienWithDetails m) {
        this.musicien = m;
        this.prenom = m.getPrenom();
        this.nom = m.getNom();
        this.classeDInstrument = m.getInstrument().getClasse();
        this.instrument = m.getInstrument();
        this.niveau = m.getNiveau();
        this.formation = m.getFormation();
    }

    public InstrumentDao getInstrumentDao() {
        return instrumentDao;
    }

    public void setMusicienDao(MusicienDao musicienDao) {
        this.musicienDao = musicienDao;
    }

    public void setInstrumentDao(InstrumentDao instrumentDao) {
        this.instrumentDao = instrumentDao;
    }

    public LiveData<List<Instrument>> getAllInstrumentsByClasse(ClasseDInstrument classe) {
        return instrumentDao.getAllInstrumentsByClasse(classe);
    }

    public void addMnfa(MusicienNiveauFormationAssociation mnfa) {
        musicienDao.addMusicienNiveauFormationAssociation(mnfa);
    }

    public long getLastIDMusicien() {
        return musicienDao.getLastId() != null ? musicienDao.getLastId().getId() : 1;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public ClasseDInstrument getClasseDInstrument() {
        return classeDInstrument;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Formation getFormation() {
        return formation;
    }

    public MusicienWithDetails getMusicien() {
        return musicien;
    }
}
