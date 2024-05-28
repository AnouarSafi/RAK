package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Client;
import com.example.myapplication.viewmodel.ClientViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClientsFragment extends Fragment {

    private ClientViewModel clientViewModel;
    private RecyclerView recyclerView;
    private ClientAdapter clientAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clients, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddClientActivity.class);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recyclerViewClients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        clientAdapter = new ClientAdapter(new ClientAdapter.OnClientActionListener() {
            @Override
            public void onEditClient(Client client) {
                Intent intent = new Intent(getActivity(), EditClientActivity.class);
                intent.putExtra("client", client);
                startActivity(intent);
            }

            @Override
            public void onDeleteClient(Client client) {
                clientViewModel.deleteClient(client);
            }
        });
        recyclerView.setAdapter(clientAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.getClients().observe(getViewLifecycleOwner(), clients -> {
            if (clients != null) {
                clientAdapter.setClients(clients);
            }
        });
    }
}
