package fr.uha.moreau.musicsgiver.ui.groupes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.uha.moreau.musicsgiver.R;

public class GroupesListFragment extends Fragment {

    private GroupesListViewModel mViewModel;

    public static GroupesListFragment newInstance() {
        return new GroupesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.groupes_list_fragment, container, false);
    }

}