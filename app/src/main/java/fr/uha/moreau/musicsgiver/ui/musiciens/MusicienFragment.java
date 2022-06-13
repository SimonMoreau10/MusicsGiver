package fr.uha.moreau.musicsgiver.ui.musiciens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import fr.uha.moreau.musicsgiver.R;
import fr.uha.moreau.musicsgiver.databinding.MusicienFragmentBinding;
import fr.uha.moreau.musicsgiver.databinding.MusicienItemBinding;
import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.Niveau;

public class MusicienFragment extends Fragment {

    private MusicienViewModel mViewModel;
    private MusicienFragmentBinding binding;
    private MusicienAdapter adapter;

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
        Spinner spinnerClasseInstrument = (Spinner) binding.spinnerClasseDInstrument;
        ClasseDInstrument[] classes = ClasseDInstrument.values();
        ArrayAdapter<ClasseDInstrument> spinadapterClasse = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, classes);
        spinadapterClasse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerClasseInstrument.setSelection(0,false);
        spinnerClasseInstrument.setAdapter(spinadapterClasse);

        Spinner spinnerNiveau = binding.spinnerNiveau;
        Niveau[] niveaux = Niveau.values();
        ArrayAdapter<Niveau> spinadapterNiveau = new ArrayAdapter<>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, niveaux);
        spinadapterNiveau.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerNiveau.setSelection(0,false);
        spinnerNiveau.setAdapter(spinadapterNiveau);

        Spinner spinnerInstrument = binding.spinnerInstrument;


        return binding.getRoot();
    }


    private class MusicienAdapter extends RecyclerView.Adapter<MusicienAdapter.ViewHolder> {
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
            return 0;
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