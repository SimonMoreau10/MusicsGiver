package fr.uha.moreau.musicsgiver.ui.instruments;

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
import fr.uha.moreau.musicsgiver.databinding.FragmentListInstrumentsBinding;
import fr.uha.moreau.musicsgiver.databinding.InstrumentItemBinding;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Instrument;

public class InstrumentsListFragment extends Fragment {

    private InstrumentsListViewModel mViewModel;
    private FragmentListInstrumentsBinding binding;
    private InstrumentAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new InstrumentAdapter();
        binding = FragmentListInstrumentsBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(divider);

        Spinner spinner = (Spinner) binding.spinnerClasseDInstrument;
        ClasseDInstrument[] classes = ClasseDInstrument.values();
        ArrayAdapter<ClasseDInstrument> spinadapter = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, classes);
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setSelection(0,false);
        spinner.setAdapter(spinadapter);

        FloatingActionButton fab = binding.floatingActionButton;


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = binding.nom.getText().toString();
                Instrument i = new Instrument(0, (ClasseDInstrument) spinner.getSelectedItem(), nom);
                mViewModel.save(i);

                adapter.notifyDataSetChanged();
            }
        });


        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InstrumentsListViewModel.class);

        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            if (appDatabase == null) return;
            mViewModel.setInstrumentDao(appDatabase.getInstrumentDao());
            mViewModel.getInstruments().observe(getViewLifecycleOwner(), instruments -> adapter.setCollection(instruments));
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.instrument_menu, menu);
    }

    private boolean doPopulate(){
        FeedDatabase feeder = new FeedDatabase();
        feeder.feed();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.instrumentMenu) {
            return doPopulate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private class InstrumentAdapter extends RecyclerView.Adapter<InstrumentAdapter.ViewHolder> {

        private List<Instrument> instruments;

        private class ViewHolder extends RecyclerView.ViewHolder {
            InstrumentItemBinding binding;
            public ViewHolder(@NonNull InstrumentItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            InstrumentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.instrument_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Instrument i = instruments.get(position);
            holder.binding.setI(i);
        }

        @Override
        public int getItemCount() {
            return instruments == null ? 0 : instruments.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setCollection(List<Instrument> instruments) {
             this.instruments = instruments;
             notifyDataSetChanged();
        }
    }
}