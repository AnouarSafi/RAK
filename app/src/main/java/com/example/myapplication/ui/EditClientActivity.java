package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.model.Client;
import com.example.myapplication.viewmodel.ClientViewModel;

public class EditClientActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private Button saveButton;
    private ClientViewModel clientViewModel;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        nameEditText = findViewById(R.id.editTextName);
        surnameEditText = findViewById(R.id.editTextSurname);
        addressEditText = findViewById(R.id.editTextAddress);
        phoneEditText = findViewById(R.id.editTextPhone);
        saveButton = findViewById(R.id.saveButton);

        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        client = (Client) getIntent().getSerializableExtra("client");
        if (client != null) {
            nameEditText.setText(client.getNom());
            surnameEditText.setText(client.getPrenom());
            addressEditText.setText(client.getAdresse());
            phoneEditText.setText(client.getNumTel());
        }

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String surname = surnameEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (!name.isEmpty() && !surname.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
                client.setNom(name);
                client.setPrenom(surname);
                client.setAdresse(address);
                client.setNumTel(phone);

                clientViewModel.updateClient(client);
                finish(); // Fermer l'activité
            }
        });
    }
}
