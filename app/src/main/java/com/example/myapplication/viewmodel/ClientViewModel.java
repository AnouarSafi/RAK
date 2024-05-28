package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.model.Client;
import com.example.myapplication.network.ApiService;
import org.json.JSONObject;
import java.util.List;

public class ClientViewModel extends AndroidViewModel {

    private ApiService apiService;
    private MutableLiveData<List<Client>> clients;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        apiService = new ApiService(application);
        clients = new MutableLiveData<>();
        fetchClients();
    }

    public MutableLiveData<List<Client>> getClients() {
        return clients;
    }

    public void fetchClients() {
        apiService.getClients(new Response.Listener<List<Client>>() {
            @Override
            public void onResponse(List<Client> response) {
                clients.setValue(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    public void createClient(Client client) {
        apiService.createClient(client, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fetchClients(); // Rafraîchir la liste des clients après la création
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    public void updateClient(Client client) {
        apiService.updateClient(client, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fetchClients(); // Rafraîchir la liste des clients après la mise à jour
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    public void deleteClient(Client client) {
        apiService.deleteClient(client.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fetchClients(); // Rafraîchir la liste des clients après la suppression
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
