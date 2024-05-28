package com.example.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Facture;
import java.util.ArrayList;
import java.util.List;

public class FactureAdapter extends RecyclerView.Adapter<FactureAdapter.FactureViewHolder> {

    private List<Facture> factures = new ArrayList<>();

    @NonNull
    @Override
    public FactureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_facture, parent, false);
        return new FactureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FactureViewHolder holder, int position) {
        Facture facture = factures.get(position);
        holder.bind(facture);
    }

    @Override
    public int getItemCount() {
        return factures.size();
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
        notifyDataSetChanged();
    }

    static class FactureViewHolder extends RecyclerView.ViewHolder {
        private final TextView montantTextView;
        private final TextView dateTextView;
        private final TextView geranceTextView;
        private final TextView clientNameTextView;

        public FactureViewHolder(@NonNull View itemView) {
            super(itemView);
            montantTextView = itemView.findViewById(R.id.montantTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            geranceTextView = itemView.findViewById(R.id.geranceTextView);
            clientNameTextView = itemView.findViewById(R.id.clientNameTextView);
        }

        public void bind(Facture facture) {
            montantTextView.setText(String.valueOf(facture.getMontant()));
            dateTextView.setText(facture.getDate());
            geranceTextView.setText(facture.getGerance());
            clientNameTextView.setText(facture.getClient().getNom() + " " + facture.getClient().getPrenom());
        }
    }
}
