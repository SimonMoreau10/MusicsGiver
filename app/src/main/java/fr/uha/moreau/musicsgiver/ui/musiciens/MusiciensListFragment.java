package fr.uha.moreau.musicsgiver.ui.musiciens;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.database.FeedDatabase;
import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.databinding.FragmentListInstrumentsBinding;
import fr.uha.moreau.musicsgiver.databinding.InstrumentItemBinding;
import fr.uha.moreau.musicsgiver.databinding.MusicienItemBinding;
import fr.uha.moreau.musicsgiver.databinding.MusiciensListFragmentBinding;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;
import fr.uha.moreau.musicsgiver.model.Niveau;

public class MusiciensListFragment extends Fragment {

    private MusiciensListViewModel mViewModel;
    private MusiciensListFragmentBinding binding;
    private MusiciensAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new MusiciensAdapter();
        binding = MusiciensListFragmentBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(divider);

        /* Spinner spinner = (Spinner) binding.spinnerInstrument;
        InstrumentDao instrumentDao = AppDatabase.get().getInstrumentDao();
        List<Instrument> instruments = instrumentDao.getAll().getValue();
        ArrayAdapter<Instrument> spinadapter = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, instruments);
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setSelection(0,false);
        spinner.setAdapter(spinadapter);*/

        FloatingActionButton fab = binding.floatingActionButton;


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* String nom = binding.nom.getText().toString();
                String prenom = binding.prNom.getText().toString();
                Musicien m = new Musicien(0, prenom, nom);
                mViewModel.addMusicien(m);
                adapter.notifyDataSetChanged();
                Instrument i = (Instrument) spinner.getSelectedItem();
                MusicienNiveauFormationAssociation mnfa = new MusicienNiveauFormationAssociation(Formation.BLUES, Niveau.INTERMEDIAIRE, m.getId(), i.getId());
                mViewModel.addAssociation(mnfa);

                adapter.notifyDataSetChanged(); */
            }
        });


        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MusiciensListViewModel.class);

        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            if (appDatabase == null) return;
            mViewModel.setMusicienDao(appDatabase.getMusicienDao());
            mViewModel.getMusiciens().observe(getViewLifecycleOwner(), musiciens -> adapter.setCollection(musiciens));
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private class MusiciensAdapter extends RecyclerView.Adapter<MusiciensAdapter.ViewHolder> {

        private List<Musicien> musiciens;

        private class ViewHolder extends RecyclerView.ViewHolder {
            MusicienItemBinding binding;
            public ViewHolder(@NonNull MusicienItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MusicienItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.musicien_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Musicien m = musiciens.get(position);
            holder.binding.setM(m);
        }

        @Override
        public int getItemCount() {
            return musiciens == null ? 0 : musiciens.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setCollection(List<Musicien> musiciens) {
            this.musiciens = musiciens;
            notifyDataSetChanged();
        }
    }
}