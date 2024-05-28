package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.model.Client;
import com.example.myapplication.network.ApiService;
import com.example.myapplication.viewmodel.ClientViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddClientActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private Button createButton;
    private ClientViewModel clientViewModel;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        nameEditText = findViewById(R.id.editTextName);
        surnameEditText = findViewById(R.id.editTextSurname);
        addressEditText = findViewById(R.id.editTextAddress);
        phoneEditText = findViewById(R.id.editTextPhone);
        createButton = findViewById(R.id.createButton);

        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        apiService = new ApiService(this);

        createButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String surname = surnameEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (validateInputs(name, surname, address, phone)) {
                Client newClient = new Client();
                newClient.setNom(name);
                newClient.setPrenom(surname);
                newClient.setAdresse(address);
                newClient.setNumTel(phone);

                createClient(newClient);
            }
        });
    }

    private boolean validateInputs(String name, String surname, String address, String phone) {
        if (name.isEmpty() || surname.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            showToast("Tous les champs doivent être remplis");
            return false;
        }
        if (!name.matches("[a-zA-Z\\s]+")) {
            showToast("Le nom ne doit contenir que des lettres et des espaces");
            return false;
        }
        if (!surname.matches("[a-zA-Z\\s]+")) {
            showToast("Le prénom ne doit contenir que des lettres et des espaces");
            return false;
        }
        if (!phone.matches("\\+?[0-9\\s\\-]+")) {
            showToast("Le numéro de téléphone n'est pas valide");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void createClient(Client client) {
        apiService.createClient(client, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Client créé avec succès
                finish(); // Fermer l'activité après création
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONArray errors = jsonObject.getJSONArray("errors");
                    StringBuilder errorMessage = new StringBuilder();
                    for (int i = 0; i < errors.length(); i++) {
                        errorMessage.append(errors.getString(i)).append("\n");
                    }
                    showToast(errorMessage.toString());
                } catch (Exception e) {
                    showToast("Une erreur est survenue");
                }
            }
        });
    }
}
