package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.myapplication.R;
import com.example.myapplication.ui.FactureAdapter;
import com.example.myapplication.model.Facture;
import com.example.myapplication.viewmodel.FactureViewModel;
import java.util.List;

public class FacturesFragment extends Fragment {

    private FactureViewModel factureViewModel;
    private FactureAdapter factureAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_factures, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFactures);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        factureAdapter = new FactureAdapter();
        recyclerView.setAdapter(factureAdapter);

        factureViewModel = new ViewModelProvider(this).get(FactureViewModel.class);
        factureViewModel.getFactures().observe(getViewLifecycleOwner(), new Observer<List<Facture>>() {
            @Override
            public void onChanged(List<Facture> factures) {
                factureAdapter.setFactures(factures);
            }
        });

        FloatingActionButton addFactureButton = view.findViewById(R.id.addFactureButton);
        addFactureButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddFactureActivity.class);
            startActivity(intent);
        });

        factureViewModel.fetchFactures();

        return view;
    }
}
