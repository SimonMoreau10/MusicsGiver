package fr.uha.moreau.musicsgiver.ui.musiciens;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.databinding.MusicienFragmentBinding;
import fr.uha.moreau.musicsgiver.databinding.MusicienItemBinding;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienWithDetails;
import fr.uha.moreau.musicsgiver.model.Niveau;

public class MusicienFragment extends Fragment {

    private MusicienViewModel mViewModel;
    private MusicienFragmentBinding binding;
    private MusicienAdapter adapter;

    private Spinner spinnerClasseInstrument;
    private Spinner spinnerInstrument;
    private Spinner spinnerNiveau;
    private Spinner spinnerFormation;

    private FloatingActionButton fab;

    private EditText prenom;
    private EditText nom;

    public static MusicienFragment newInstance() {
        return new MusicienFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new MusicienAdapter();
        binding = MusicienFragmentBinding.inflate(inflater, container, false);

        prenom = binding.editTextFirstName;
        nom = binding.editTextLastName;

        spinnerClasseInstrument = binding.spinnerClasseDInstrument;
        ClasseDInstrument[] classes = ClasseDInstrument.values();
        ArrayAdapter<ClasseDInstrument> spinadapterClasse = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, classes);
        spinadapterClasse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerClasseInstrument.setSelection(0,false);
        spinnerClasseInstrument.setAdapter(spinadapterClasse);

        spinnerNiveau = binding.spinnerNiveau;
        Niveau[] niveaux = Niveau.values();
        ArrayAdapter<Niveau> spinadapterNiveau = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, niveaux);
        spinadapterNiveau.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerNiveau.setSelection(0,false);
        spinnerNiveau.setAdapter(spinadapterNiveau);

        fab = binding.floatingActionButton;
        binding.floatingActionButton.setOnClickListener(
                view -> NavHostFragment.findNavController(this).navigate(R.id.action_musicienFragment_to_musiciensListFragment)
        );

        spinnerInstrument = binding.spinnerInstrument;

        spinnerFormation = binding.spinnerFormation;
        Formation[] formations = Formation.values();
        ArrayAdapter<Formation> spinadapterFormation = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, formations);
        spinadapterFormation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerFormation.setSelection(0,false);
        spinnerFormation.setAdapter(spinadapterFormation);

        return binding.getRoot();
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MusicienViewModel.class);
        long id = 0;
        if(getArguments() != null) {
            id = getArguments().getLong("id");
        }
        long finalId = id;
        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            System.out.println(appDatabase);
            if (appDatabase == null) return;
            mViewModel.setMusicienDao(appDatabase.getMusicienDao());
            mViewModel.setInstrumentDao(appDatabase.getInstrumentDao());
            ClasseDInstrument classeDInstrument = (ClasseDInstrument) spinnerClasseInstrument.getSelectedItem();
            mViewModel.getAllInstrumentsByClasse(classeDInstrument).observe(getViewLifecycleOwner(), is -> {
                Instrument[] instruments = is.toArray(new Instrument[0]);
                ArrayAdapter<Instrument> spinadapter = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, instruments);
                spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinnerInstrument.setSelection(0,false);
                spinnerInstrument.setAdapter(spinadapter);
            });
            if (finalId != 0) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        MusicienWithDetails m = new MusicienWithDetails(finalId, mViewModel.getMusicienDao(), mViewModel.getInstrumentDao());
                        fab.setVisibility(View.INVISIBLE);
                        prenom.setText(m.getPrenom());
                        nom.setText(m.getNom());
                        spinnerFormation.setSelection(getIndex(spinnerFormation, m.getFormation().toString()));
                        spinnerNiveau.setSelection(getIndex(spinnerNiveau, m.getNiveau().toString()));
                        spinnerInstrument.setSelection(getIndex(spinnerInstrument, m.getInstrument().getNom()));
                        spinnerClasseInstrument.setSelection(getIndex(spinnerClasseInstrument, m.getInstrument().getClasseAAfficher()));
                        prenom.setEnabled(false);
                        nom.setEnabled(false);
                        spinnerFormation.setEnabled(false);
                        spinnerInstrument.setEnabled(false);
                        spinnerClasseInstrument.setEnabled(false);
                        spinnerNiveau.setEnabled(false);
                    }
                });
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = binding.editTextLastName.getText().toString();
                String prenom = binding.editTextFirstName.getText().toString();
                Formation formation = (Formation) binding.spinnerFormation.getSelectedItem();
                Niveau niveau = (Niveau) binding.spinnerNiveau.getSelectedItem();
                Instrument instrument = (Instrument) binding.spinnerInstrument.getSelectedItem();

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Musicien m = new Musicien(0, prenom, nom);
                        mViewModel.addMusicien(m);
                    }
                });
                NavHostFragment.findNavController(MusicienFragment.this).navigate((MusicienFragmentDirections.actionMusicienFragmentToMusiciensListFragment()));
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        MusicienNiveauFormationAssociation mnfa = new MusicienNiveauFormationAssociation(formation, niveau, mViewModel.getLastIDMusicien(), instrument.getId());
                        mViewModel.addMnfa(mnfa);
                    }
                });

            }
        });

        spinnerClasseInstrument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
                    if (appDatabase == null) return;
                    ClasseDInstrument classeDInstrument = (ClasseDInstrument) spinnerClasseInstrument.getSelectedItem();
                    mViewModel.getAllInstrumentsByClasse(classeDInstrument).observe(getViewLifecycleOwner(), is -> {
                        Instrument[] instruments = is.toArray(new Instrument[0]);
                        ArrayAdapter<Instrument> spinadapter = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, instruments);
                        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spinnerInstrument.setSelection(0,false);
                        spinnerInstrument.setAdapter(spinadapter);
                    });
                    System.out.println("dao " + mViewModel.getMusicienDao());
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        /*
        if (id != 0) {
            MusicienWithDetails m = new MusicienWithDetails(id, mViewModel.getMusicienDao(), mViewModel.getInstrumentDao());
            fab.setVisibility(View.INVISIBLE);
            prenom.setText(m.getPrenom());
            nom.setText(m.getNom());
            spinnerFormation.setSelection(getIndex(spinnerFormation, m.getFormation().toString()));
            spinnerNiveau.setSelection(getIndex(spinnerNiveau, m.getNiveau().toString()));
            spinnerInstrument.setSelection(getIndex(spinnerInstrument, m.getInstrument().getNom()));
            spinnerClasseInstrument.setSelection(getIndex(spinnerClasseInstrument, m.getInstrument().getClasseAAfficher()));
        } */
    }


    private class MusicienAdapter extends RecyclerView.Adapter<MusicienAdapter.ViewHolder> {

        private MusicienWithDetails musicien;

        public void setMusicien(MusicienWithDetails m) {
            this.musicien = musicien;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @NonNull
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            MusicienItemBinding binding;

            public ViewHolder(@NonNull MusicienItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

}