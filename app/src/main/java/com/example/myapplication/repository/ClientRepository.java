package com.example.myapplication.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Response;
import com.example.myapplication.model.Client;
import com.example.myapplication.network.ApiService;
import org.json.JSONObject;
import java.util.List;

public class ClientRepository {

    private ApiService apiService;

    public ClientRepository(Context context) {
        apiService = new ApiService(context);
    }

    public LiveData<List<Client>> getClients() {
        final MutableLiveData<List<Client>> data = new MutableLiveData<>();
        apiService.getClients(
                data::setValue,
                error -> data.setValue(null)
        );
        return data;
    }

    public void createClient(Client client) {
        apiService.createClient(client, response -> {
            // Handle successful creation if necessary
        }, error -> {
            // Handle error
        });
    }

    public void updateClient(Client client) {
        apiService.updateClient(client, response -> {
            // Handle successful update if necessary
        }, error -> {
            // Handle error
        });
    }

    public void deleteClient(Client client) {
        apiService.deleteClient(client.getId(), response -> {
            // Handle successful deletion if necessary
        }, error -> {
            // Handle error
        });
    }
}
