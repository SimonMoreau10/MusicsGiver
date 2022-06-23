package fr.uha.moreau.musicsgiver.ui.groupes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
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


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.databinding.GroupeItemBinding;
import fr.uha.moreau.musicsgiver.databinding.GroupesListFragmentBinding;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.ui.ItemSwipeCallback;
import fr.uha.moreau.musicsgiver.ui.musiciens.MusiciensListFragment;
import fr.uha.moreau.musicsgiver.ui.musiciens.MusiciensListFragmentDirections;

public class GroupesListFragment extends Fragment {

    private GroupesListViewModel mViewModel;
    private GroupesListAdapter adapter;
    private GroupesListFragmentBinding binding;

    private FloatingActionButton fab;
    private Spinner spinnerFormation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new GroupesListFragment.GroupesListAdapter();
        binding = GroupesListFragmentBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(divider);

        spinnerFormation = (Spinner) binding.spinnerFormation;
        Formation[] formations = Formation.values();
        ArrayAdapter<Formation> spinadapter = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, formations);
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerFormation.setSelection(0,false);
        spinnerFormation.setAdapter(spinadapter);

        fab = binding.floatingActionButton;

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemSwipeCallback(getContext(), ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT , new ItemSwipeCallback.SwipeListener() {
                    @Override
                    public void onSwiped(int direction, int position) {
                        Groupe g = adapter.groupes.get(position);
                        switch (direction) {
                            case ItemTouchHelper.LEFT:
                                Executor executor = Executors.newSingleThreadExecutor();
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mViewModel.deleteGroupe(g);
                                    }
                                });
                                break;
                            case ItemTouchHelper.RIGHT:
                                GroupesListFragmentDirections.ActionGroupesListFragmentToGroupeFragment action = GroupesListFragmentDirections.actionGroupesListFragmentToGroupeFragment();
                                action.setIdGroupe(g.getId());
                                NavHostFragment.findNavController(GroupesListFragment.this).navigate(action);
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
        mViewModel = new ViewModelProvider(this).get(GroupesListViewModel.class);
        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            if (appDatabase == null) return;
            mViewModel.setGroupeDao(appDatabase.getGroupeDao());
            mViewModel.getGroupes().observe(getViewLifecycleOwner(), groupes -> adapter.setCollection(groupes));
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Groupe g = new Groupe(0, binding.editTextNom.getText().toString(), (Formation) binding.spinnerFormation.getSelectedItem());
                mViewModel.addGroupe(g);
                NavHostFragment.findNavController(GroupesListFragment.this).navigate(GroupesListFragmentDirections.actionGroupesListFragmentToGroupeFragment());
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.vueParFormation) {
            NavHostFragment.findNavController(GroupesListFragment.this).navigate(GroupesListFragmentDirections.actionGroupesListFragmentToMusicienParFormation());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.groupe_menu, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private class GroupesListAdapter extends RecyclerView.Adapter<GroupesListAdapter.ViewHolder> {

        private List<Groupe> groupes;

        private class ViewHolder extends RecyclerView.ViewHolder {
            GroupeItemBinding binding;
            public ViewHolder(@NonNull GroupeItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        @NonNull
        @Override
        public GroupesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GroupeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.groupe_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            return new ViewHolder(binding);
        }


        @Override
        public void onBindViewHolder(@NonNull GroupesListAdapter.ViewHolder holder, int position) {
            Groupe g = groupes.get(position);
            holder.binding.setG(g);
        }

        @Override
        public int getItemCount() {
            return groupes == null ? 0 : groupes.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setCollection(List<Groupe> groupes) {
            this.groupes = groupes;
            notifyDataSetChanged();
        }
    }
}

