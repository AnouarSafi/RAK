package com.example.myapplication.network;

import android.content.Context;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.model.Client;
import com.example.myapplication.model.Facture;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ApiService {

    private static final String BASE_URL_CLIENT = "http://192.168.137.35:8000/api/clients";
    private static final String CREATE_CLIENT_URL = "http://192.168.137.35:8000/create-client";
    private static final String BASE_URL_FACTURE = "http://192.168.137.35:8000/api/factures";
    private static final String CREATE_FACTURE_URL = "http://192.168.137.35:8000/create-facture";
    private RequestQueue requestQueue;

    public ApiService(Context context) {
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public void getClients(Response.Listener<List<Client>> listener, Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL_CLIENT,
                null,
                response -> {
                    List<Client> clients = parseJsonToClientList(response);
                    listener.onResponse(clients);
                },
                errorListener
        );
        requestQueue.add(request);
    }

    public void createClient(Client client, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nom", client.getNom());
            jsonObject.put("prenom", client.getPrenom());
            jsonObject.put("adresse", client.getAdresse());
            jsonObject.put("numTel", client.getNumTel());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                CREATE_CLIENT_URL,
                jsonObject,
                listener,
                errorListener
        );
        requestQueue.add(request);
    }

    public void updateClient(Client client, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL_CLIENT + "/" + client.getId();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nom", client.getNom());
            jsonObject.put("prenom", client.getPrenom());
            jsonObject.put("adresse", client.getAdresse());
            jsonObject.put("numTel", client.getNumTel());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonObject,
                listener,
                errorListener
        );
        requestQueue.add(request);
    }

    public void deleteClient(int clientId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL_CLIENT + "/" + clientId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                listener,
                errorListener
        );
        requestQueue.add(request);
    }

    public void getFactures(Response.Listener<List<Facture>> listener, Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL_FACTURE,
                null,
                response -> {
                    List<Facture> factures = parseJsonToFactureList(response);
                    listener.onResponse(factures);
                },
                errorListener
        );
        requestQueue.add(request);
    }

    public void createFacture(Facture facture, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("montant", facture.getMontant());
            jsonObject.put("date", facture.getDate());
            jsonObject.put("gerance", facture.getGerance());
            jsonObject.put("client_id", facture.getClient().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, CREATE_FACTURE_URL, jsonObject,
                listener, errorListener) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 400) {
                    try {
                        String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        return Response.error(new VolleyError(json));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new VolleyError(e));
                    }
                }
                return super.parseNetworkResponse(response);
            }
        };

        requestQueue.add(request);
    }

    public void updateFacture(Facture facture, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL_FACTURE + "/" + facture.getId();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("montant", facture.getMontant());
            jsonObject.put("date", facture.getDate());
            jsonObject.put("gerance", facture.getGerance());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                listener, errorListener) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 400) {
                    try {
                        String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        return Response.error(new VolleyError(json));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new VolleyError(e));
                    }
                }
                return super.parseNetworkResponse(response);
            }
        };

        requestQueue.add(request);
    }

    public void deleteFacture(int factureId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL_FACTURE + "/" + factureId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                listener,
                errorListener
        );
        requestQueue.add(request);
    }

    private List<Client> parseJsonToClientList(JSONArray jsonArray) {
        List<Client> clients = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Client client = new Client();
                client.setId(jsonObject.getInt("id"));
                client.setNom(jsonObject.getString("nom"));
                client.setPrenom(jsonObject.getString("prenom"));
                client.setAdresse(jsonObject.getString("adresse"));
                client.setNumTel(jsonObject.getString("numTel"));
                clients.add(client);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private List<Facture> parseJsonToFactureList(JSONArray jsonArray) {
        List<Facture> factures = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Facture facture = new Facture();
                facture.setId(jsonObject.getInt("id"));
                facture.setMontant((float) jsonObject.getDouble("montant"));
                facture.setDate(jsonObject.getString("date"));
                facture.setGerance(jsonObject.getString("gerance"));

                // Parse client information
                JSONObject clientJson = jsonObject.getJSONObject("client");
                Client client = new Client();
                client.setId(clientJson.getInt("id"));
                client.setNom(clientJson.getString("nom"));
                client.setPrenom(clientJson.getString("prenom"));
                client.setAdresse(clientJson.getString("adresse"));
                client.setNumTel(clientJson.getString("numTel"));

                facture.setClient(client);

                factures.add(facture);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return factures;
    }

}
