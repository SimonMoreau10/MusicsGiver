package fr.uha.moreau.musicsgiver.ui.groupes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.databinding.MusicienParFormationItemBinding;
import fr.uha.moreau.musicsgiver.databinding.MusiciensParFormationBinding;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;
import fr.uha.moreau.musicsgiver.ui.ItemSwipeCallback;

public class MusicienParFormation extends Fragment {

    private MusicienParFormationViewModel mViewModel;
    private MusiciensParFormationBinding binding;
    private MusicienParFormationAdapter adapter;

    private Spinner spinnerFormation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new MusicienParFormationAdapter();
        binding = MusiciensParFormationBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(divider);

        spinnerFormation = binding.spinnerFormation;

        Formation[] formations = Formation.values();
        ArrayAdapter<Formation> spinadapterFormation = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, formations);
        spinadapterFormation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerFormation.setSelection(0,false);
        spinnerFormation.setAdapter(spinadapterFormation);

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemSwipeCallback(getContext(), ItemTouchHelper.RIGHT , new ItemSwipeCallback.SwipeListener() {
                    @Override
                    public void onSwiped(int direction, int position) {
                        long id = adapter.mnfas.get(position).getMid();
                        switch (direction) {
                            case ItemTouchHelper.RIGHT:
                                MusicienParFormationDirections.ActionMusiciensParFormationToMusicienFragment action = MusicienParFormationDirections.actionMusiciensParFormationToMusicienFragment();
                                action.setIdMusicien(id);
                                NavHostFragment.findNavController(MusicienParFormation.this).navigate(action);

                                break;
                        }
                    }
                })
        );
        touchHelper.attachToRecyclerView(binding.recyclerView);

        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MusicienParFormationViewModel.class);

        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            if (appDatabase == null) return;
            mViewModel.setMusicienDao(appDatabase.getMusicienDao());
            mViewModel.setInstrumentDao(appDatabase.getInstrumentDao());
            Formation formation = (Formation) spinnerFormation.getSelectedItem();
            mViewModel.getMusicienNiveauFormationAssociationByFormation(formation).observe(getViewLifecycleOwner(), mnfas -> adapter.setCollectionAssociations(mnfas));
            mViewModel.getMusiciensByFormation(formation).observe(getViewLifecycleOwner(), musiciens -> adapter.setCollection(musiciens));
        });

        spinnerFormation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
                    if (appDatabase == null) return;
                    Formation formation = (Formation) spinnerFormation.getSelectedItem();
                    mViewModel.getMusicienNiveauFormationAssociationByFormation(formation).observe(getViewLifecycleOwner(), mnfas -> adapter.setCollectionAssociations(mnfas));
                    mViewModel.getMusiciensByFormation(formation).observe(getViewLifecycleOwner(), musiciens -> binding.recyclerView.setAdapter(adapter));
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing to do
            }
        });
    }


    private class MusicienParFormationAdapter extends RecyclerView.Adapter<MusicienParFormationAdapter.ViewHolder> {

        private List<MusicienNiveauFormationAssociation> mnfas;
        private List<Musicien> musiciens;

        public void setCollectionAssociations(List<MusicienNiveauFormationAssociation> mnfas) {
            this.mnfas = mnfas;
            notifyDataSetChanged();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            MusicienParFormationItemBinding binding;
            public ViewHolder(@NonNull MusicienParFormationItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MusicienParFormationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.musicien_par_formation_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MusicienNiveauFormationAssociation mnfa = mnfas.get(position);
            Executor executor = Executors.newSingleThreadExecutor();
            final MusicienDao[] musicienDao = new MusicienDao[1];
            final InstrumentDao[] instrumentDao = new InstrumentDao[1];
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    musicienDao[0] = mViewModel.getMusicienDao();
                    instrumentDao[0] = mViewModel.getInstrumentDao();
                    Musicien m = musicienDao[0].getById(mnfa.getMid());
                    Instrument i = instrumentDao[0].getById(mnfa.getIid());
                    holder.binding.setI(i);
                    holder.binding.setM(m);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mnfas == null ? 0 : mnfas.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setCollection(List<Musicien> musiciens) {
            this.musiciens = musiciens;
            notifyDataSetChanged();
        }

    }
}