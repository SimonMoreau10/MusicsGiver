package fr.uha.moreau.musicsgiver.ui.instruments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Instrument;

public class InstrumentViewModel extends ViewModel {
    private InstrumentDao instrumentDao;
    private LiveData<Instrument> instrument;
    private MutableLiveData<Long> id = new MutableLiveData<>();
    private LiveData<ClasseDInstrument> classeDInstrument;
    private LiveData<String> nom;

    public void setInstrumentDao(InstrumentDao instrumentDao) {
        this.instrumentDao = instrumentDao;
        this.instrument = Transformations.switchMap(id, instrumentDao::getById);
        this.classeDInstrument = Transformations.map(instrument, Instrument::getClasse);
        this.nom = Transformations.map(instrument, Instrument::getNom);
    }

    public LiveData<Instrument> getInstrument() {
        return instrument;
    }
    public LiveData<ClasseDInstrument> getClasseDInstrument() {
        return classeDInstrument;
    }
    public LiveData<String> getNom() {
        return nom;
    }
    public MutableLiveData<Long> getId() {
        return id;
    }

    public void setId(MutableLiveData<Long> id) {
        this.id = id;
    }
    public void setInstrument(LiveData<Instrument> instrument) {
        this.instrument = instrument;
    }
    public void setClasseDInstrument(LiveData<ClasseDInstrument> classeDInstrument) {
        this.classeDInstrument = classeDInstrument;
    }
    public void setNom(LiveData<String> nom) {
        this.nom = nom;
    }
}