package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.model.Facture;
import com.example.myapplication.viewmodel.FactureViewModel;

public class EditFactureActivity extends AppCompatActivity {

    private EditText montantEditText;
    private EditText dateEditText;
    private EditText geranceEditText;
    private EditText clientIdEditText;
    private Button saveButton;
    private FactureViewModel factureViewModel;
    private Facture facture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_facture);

        montantEditText = findViewById(R.id.editTextMontant);
        dateEditText = findViewById(R.id.editTextDate);
        geranceEditText = findViewById(R.id.editTextGerance);
        clientIdEditText = findViewById(R.id.editTextClientId);
        saveButton = findViewById(R.id.saveButton);

        factureViewModel = new ViewModelProvider(this).get(FactureViewModel.class);

        facture = (Facture) getIntent().getSerializableExtra("facture");
        if (facture != null) {
            montantEditText.setText(String.valueOf(facture.getMontant()));
            dateEditText.setText(facture.getDate());
            geranceEditText.setText(facture.getGerance());
            clientIdEditText.setText(String.valueOf(facture.getClient().getId()));
        }

        saveButton.setOnClickListener(v -> {
            float montant = Float.parseFloat(montantEditText.getText().toString().trim());
            String date = dateEditText.getText().toString().trim();
            String gerance = geranceEditText.getText().toString().trim();
            int clientId = Integer.parseInt(clientIdEditText.getText().toString().trim());

            if (!date.isEmpty() && !gerance.isEmpty()) {
                facture.setMontant(montant);
                facture.setDate(date);
                facture.setGerance(gerance);
                facture.getClient().setId(clientId);

                factureViewModel.updateFacture(facture);
                finish(); // Fermer l'activité après la mise à jour de la facture
            }
        });
    }
}
