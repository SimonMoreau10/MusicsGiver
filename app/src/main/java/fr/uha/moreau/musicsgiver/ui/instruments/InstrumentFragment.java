package fr.uha.moreau.musicsgiver.ui.instruments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Instrument;

public class InstrumentFragment extends Fragment {

    private InstrumentViewModel mViewModel;


    public static InstrumentFragment newInstance() {
        return new InstrumentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.instrument_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InstrumentViewModel.class);
        // Spinner spinner = new Spinner
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InstrumentViewModel.class);

        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            if(appDatabase == null) return;
            // mViewModel.setInstrument(appDatabase.getInstrumentDao());
        });
    }
}