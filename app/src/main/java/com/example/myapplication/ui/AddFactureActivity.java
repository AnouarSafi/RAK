package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.model.Client;
import com.example.myapplication.model.Facture;
import com.example.myapplication.viewmodel.FactureViewModel;
import com.android.volley.VolleyError;

public class AddFactureActivity extends AppCompatActivity {

    private EditText clientIdEditText;
    private EditText montantEditText;
    private EditText dateEditText;
    private EditText geranceEditText;
    private Button createButton;
    private FactureViewModel factureViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_facture);

        clientIdEditText = findViewById(R.id.editTextClientId);
        montantEditText = findViewById(R.id.editTextMontant);
        dateEditText = findViewById(R.id.editTextDate);
        geranceEditText = findViewById(R.id.editTextGerance);
        createButton = findViewById(R.id.createButton);

        factureViewModel = new ViewModelProvider(this).get(FactureViewModel.class);

        createButton.setOnClickListener(v -> {
            String clientIdStr = clientIdEditText.getText().toString().trim();
            String montantStr = montantEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String gerance = geranceEditText.getText().toString().trim();

            if (!clientIdStr.isEmpty() && !montantStr.isEmpty() && !date.isEmpty() && !gerance.isEmpty()) {
                int clientId = Integer.parseInt(clientIdStr);
                float montant = Float.parseFloat(montantStr);

                Facture newFacture = new Facture();
                newFacture.setClient(new Client(clientId));
                newFacture.setMontant(montant);
                newFacture.setDate(date); // Assurez-vous que le format de la date est YYYY-MM-DD
                newFacture.setGerance(gerance);

                factureViewModel.createFacture(newFacture, new FactureViewModel.CreateFactureCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddFactureActivity.this, "Facture créée avec succès", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(AddFactureActivity.this, "Erreur lors de la création de la facture: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
