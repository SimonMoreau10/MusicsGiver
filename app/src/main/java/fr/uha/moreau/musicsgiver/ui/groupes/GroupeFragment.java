package fr.uha.moreau.musicsgiver.ui.groupes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.databinding.GroupeFragmentBinding;
import fr.uha.moreau.musicsgiver.databinding.MusicienInGroupItemBinding;
import fr.uha.moreau.musicsgiver.databinding.MusicienItemBinding;
import fr.uha.moreau.musicsgiver.databinding.MusiciensListFragmentBinding;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienGroupeAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienWithDetails;
import fr.uha.moreau.musicsgiver.ui.ItemSwipeCallback;
import fr.uha.moreau.musicsgiver.ui.musiciens.MusicienFragment;
import fr.uha.moreau.musicsgiver.ui.musiciens.MusiciensListFragment;
import fr.uha.moreau.musicsgiver.ui.musiciens.MusiciensListFragmentDirections;
import fr.uha.moreau.musicsgiver.ui.musiciens.MusiciensListViewModel;

public class GroupeFragment extends Fragment {

    private GroupeViewModel mViewModel;
    private GroupeFragmentBinding binding;
    private GroupeAdapter adapter;
    private Spinner spinnerFormation;

    private FloatingActionButton fab;
    private TextView editTextView;
    private long groupeId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new GroupeAdapter();
        binding = GroupeFragmentBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(divider);

        fab = binding.floatingActionButton;
        fab.setOnClickListener(
                view -> {
                    // ajouter un zikos, sachant que la formation est sett??e
                }
        );
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemSwipeCallback(getContext(), ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT , new ItemSwipeCallback.SwipeListener() {
                    @Override
                    public void onSwiped(int direction, int position) {
                        switch (direction) {
                            case ItemTouchHelper.RIGHT:
                                GroupeFragmentDirections.ActionGroupeFragmentToMusicienFragment action = GroupeFragmentDirections.actionGroupeFragmentToMusicienFragment();
                                Thread T1 = new Thread () {
                                    public void run() {
                                        action.setIdMusicien(mViewModel.getMusicienDao().getById(adapter.mgas.get(position).getMid()).getId());
                                    }
                                };
                                T1.start();
                                try {
                                    T1.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                NavHostFragment.findNavController(GroupeFragment.this).navigate(action);

                                break;
                            case ItemTouchHelper.LEFT:
                                Thread T2 = new Thread () {
                                    public void run() {
                                        mViewModel.delete(adapter.mgas.get(position));
                                    }
                                };
                                T2.start();
                                try {
                                    T2.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // delete
                                break;
                        }
                    }
                })
        );
        touchHelper.attachToRecyclerView(binding.recyclerView);

        spinnerFormation = binding.spinnerFormation;
        spinnerFormation = binding.spinnerFormation;
        Formation[] formations = Formation.values();
        ArrayAdapter<Formation> spinadapterFormation = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, formations);
        spinadapterFormation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerFormation.setSelection(0,false);
        spinnerFormation.setAdapter(spinadapterFormation);

        spinnerFormation.setVisibility(View.GONE);

        editTextView = binding.nom;
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GroupeViewModel.class);
        if (getArguments() != null) {
            groupeId = getArguments().getLong("idGroupe");
        }

        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            if (appDatabase == null) return;
            mViewModel.setGroupeDao(appDatabase.getGroupeDao());
            mViewModel.setMusicienDao(appDatabase.getMusicienDao());

            mViewModel.getMusicienGroupeAssociationByGID(groupeId).observe(getViewLifecycleOwner(), mgas -> adapter.setCollectionAssociations(mgas));;

        });
/*        if (groupeId == 0) {
            Thread T1 = new Thread() {
                public void run() {
                   groupeId = mViewModel.getGroupeDao().getLastId().getId();
                    Groupe g = mViewModel.getGroupeDao().getById(groupeId);
                    editTextView.setText(g.getName(), TextView.BufferType.EDITABLE);
                    spinnerFormation.setSelection(getIndex(spinnerFormation, g.getFormation().toString()));
                }
            };
            T1.start();
            try {
                T1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } */

        /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusiciensListFragmentDirections.ActionMusiciensListFragmentToMusicienFragment action = MusiciensListFragmentDirections.actionMusiciensListFragmentToMusicienFragment();
                action.setId(0);
                NavHostFragment.findNavController(MusiciensListFragment.this).navigate(action);
            }
        }); */
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
    public void onCreate(@Nullable Bundle savedInstanceState) {;
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    private class GroupeAdapter extends RecyclerView.Adapter<GroupeAdapter.ViewHolder> {

        private List<MusicienGroupeAssociation> mgas;
        private List<Musicien> musiciens;

        public void setCollectionAssociations(List<MusicienGroupeAssociation> mgas) {
            this.mgas = mgas;
            notifyDataSetChanged();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            MusicienInGroupItemBinding binding;
            public ViewHolder(@NonNull MusicienInGroupItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MusicienInGroupItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from((parent.getContext())), R.layout.musicien_in_group_item, parent, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MusicienGroupeAssociation mga = mgas.get(position);
            Executor executor = Executors.newSingleThreadExecutor();
            final MusicienDao[] musicienDao = new MusicienDao[1];
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    musicienDao[0] = mViewModel.getMusicienDao();
                    Musicien m = musicienDao[0].getById(mga.getMid());
                    holder.binding.setM(m);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mgas == null ? 0 : mgas.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setCollection(List<Musicien> musiciens) {
            this.musiciens = musiciens;
            notifyDataSetChanged();

        }

    }
}