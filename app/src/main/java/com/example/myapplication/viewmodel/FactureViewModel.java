package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.android.volley.VolleyError;
import com.example.myapplication.model.Facture;
import com.example.myapplication.repository.FactureRepository;
import java.util.List;

public class FactureViewModel extends AndroidViewModel {
    private final FactureRepository factureRepository;

    public FactureViewModel(Application application) {
        super(application);
        factureRepository = new FactureRepository(application);
    }

    public LiveData<List<Facture>> getFactures() {
        return factureRepository.getFactures();
    }

    public void fetchFactures() {
        // Optional if needed to trigger fetching
    }

    public void createFacture(Facture facture, CreateFactureCallback callback) {
        factureRepository.createFacture(facture, callback);
    }

    public void updateFacture(Facture facture) {
        factureRepository.updateFacture(facture);
    }

    public void deleteFacture(Facture facture) {
        factureRepository.deleteFacture(facture);
    }

    public interface CreateFactureCallback {
        void onSuccess();
        void onError(VolleyError error);
    }
}
