package com.example.myapplication.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.model.Facture;
import com.example.myapplication.network.ApiService;
import com.example.myapplication.viewmodel.FactureViewModel;

import java.util.List;

public class FactureRepository {

    private ApiService apiService;

    public FactureRepository(Context context) {
        apiService = new ApiService(context);
    }

    public LiveData<List<Facture>> getFactures() {
        final MutableLiveData<List<Facture>> data = new MutableLiveData<>();
        apiService.getFactures(
                data::setValue,
                error -> data.setValue(null)
        );
        return data;
    }

    public void createFacture(Facture facture, FactureViewModel.CreateFactureCallback callback) {
        apiService.createFacture(facture, response -> {
            callback.onSuccess();
        }, callback::onError);
    }

    public void updateFacture(Facture facture) {
        apiService.updateFacture(facture, response -> {
            // Handle successful update if necessary
        }, error -> {
            // Handle error
        });
    }

    public void deleteFacture(Facture facture) {
        apiService.deleteFacture(facture.getId(), response -> {
            // Handle successful deletion if necessary
        }, error -> {
            // Handle error
        });
    }
}
