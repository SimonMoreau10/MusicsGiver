package fr.uha.moreau.musicsgiver.ui.instruments;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.model.Instrument;

public class InstrumentsListViewModel extends ViewModel {
    private InstrumentDao instrumentDao;
    private MediatorLiveData<List<Instrument>> instruments;

    public void setInstrumentDao(InstrumentDao instrumentDao) {
        this.instrumentDao = instrumentDao;
        this.instruments = new MediatorLiveData<>();
        this.instruments.addSource(instrumentDao.getAll(), instruments::setValue);
    }

    public LiveData<List<Instrument>> getInstruments() {
        return instruments;
    }

    // public void

}
