package fr.uha.moreau.musicsgiver.ui.musiciens;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.uha.moreau.musicsgiver.R;

public class MusiciensListFragment extends Fragment {

    private MusiciensListViewModel mViewModel;

    public static MusiciensListFragment newInstance() {
        return new MusiciensListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.musiciens_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MusiciensListViewModel.class);
        // TODO: Use the ViewModel
    }

}