package fr.uha.moreau.musicsgiver.ui.musiciens;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.database.AppDatabase;
import fr.uha.moreau.musicsgiver.database.FeedDatabase;
import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;
import fr.uha.moreau.musicsgiver.databinding.FragmentListInstrumentsBinding;
import fr.uha.moreau.musicsgiver.databinding.InstrumentItemBinding;
import fr.uha.moreau.musicsgiver.databinding.MusicienItemBinding;
import fr.uha.moreau.musicsgiver.databinding.MusiciensListFragmentBinding;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienWithDetails;
import fr.uha.moreau.musicsgiver.model.Niveau;
import fr.uha.moreau.musicsgiver.ui.ItemSwipeCallback;
import fr.uha.moreau.musicsgiver.ui.instruments.InstrumentsListFragment;

public class MusiciensListFragment extends Fragment {

    private MusiciensListViewModel mViewModel;
    private MusiciensListFragmentBinding binding;
    private MusiciensAdapter adapter;

    private FloatingActionButton fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adapter = new MusiciensAdapter();
        binding = MusiciensListFragmentBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(divider);

        fab = binding.floatingActionButton;
        fab.setOnClickListener(
                view -> {
                    MusiciensListFragmentDirections.ActionMusiciensListFragmentToMusicienFragment action = MusiciensListFragmentDirections.actionMusiciensListFragmentToMusicienFragment();
                    action.setIdMusicien(0);
                    NavHostFragment.findNavController(this).navigate(R.id.action_musiciensListFragment_to_musicienFragment);
                }
        );

        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemSwipeCallback(getContext(), ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, new ItemSwipeCallback.SwipeListener() {
                    @Override
                    public void onSwiped(int direction, int position) {
                        switch (direction) {
                            case ItemTouchHelper.RIGHT:

                                MusiciensListFragmentDirections.ActionMusiciensListFragmentToMusicienFragment action = MusiciensListFragmentDirections.actionMusiciensListFragmentToMusicienFragment();
                                action.setIdMusicien(adapter.musiciens.get(position).getId());
                                NavHostFragment.findNavController(MusiciensListFragment.this).navigate(action);
                                break;
                            case ItemTouchHelper.LEFT:
                                Executor executor = Executors.newSingleThreadExecutor();
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mViewModel.deleteMusicienWithDetails(new MusicienWithDetails(adapter.musiciens.get(position).getId(), mViewModel.getMusicienDao(), mViewModel.getInstrumentDao()));
                                    }
                                });
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
        mViewModel = new ViewModelProvider(this).get(MusiciensListViewModel.class);

        AppDatabase.isReady().observe(getViewLifecycleOwner(), appDatabase -> {
            if (appDatabase == null) return;
            mViewModel.setMusicienDao(appDatabase.getMusicienDao());
            mViewModel.setInstrumentDao(appDatabase.getInstrumentDao());
            mViewModel.getMusicienNiveauFormationAssociation().observe(getViewLifecycleOwner(), mnfas -> adapter.setCollectionAssociations(mnfas));
            mViewModel.getMusiciens().observe(getViewLifecycleOwner(), musiciens -> adapter.setCollection(musiciens));
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusiciensListFragmentDirections.ActionMusiciensListFragmentToMusicienFragment action = MusiciensListFragmentDirections.actionMusiciensListFragmentToMusicienFragment();
                action.setIdMusicien(0);
                NavHostFragment.findNavController(MusiciensListFragment.this).navigate(action);
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {;
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private class MusiciensAdapter extends RecyclerView.Adapter<MusiciensAdapter.ViewHolder> {

        private List<MusicienNiveauFormationAssociation> mnfas;
        private List<Musicien> musiciens;

        public void setCollectionAssociations(List<MusicienNiveauFormationAssociation> mnfas) {
            this.mnfas = mnfas;
            notifyDataSetChanged();
        }

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