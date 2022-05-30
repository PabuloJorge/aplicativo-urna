package com.example.urna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FimVotacao extends AppCompatActivity {
    TextView tv_resultadoVotacao;
    Intent i;
    String votoVereador, votoPrefeito, cpfEleitor;
    RequestQueue requestQueue;
    String url = "https://apiurna.000webhostapp.com/postVoto.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim_votacao);

        tv_resultadoVotacao = findViewById(R.id.tv_resultadoVotacao);
        i = getIntent();

        requestQueue = Volley.newRequestQueue(this);

        votoVereador = i.getStringExtra("votoVereador");
        votoPrefeito = i.getStringExtra("votoPrefeito");
        cpfEleitor = i.getStringExtra("cpfEleitor");

        postVoto();

        tv_resultadoVotacao.setText("Voto vereador: " + votoVereador + "\nVoto Prefeito: " + votoPrefeito+"\nCPF: "+cpfEleitor);
    }
    private void postVoto() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean isErro = jsonObject.getBoolean("erro");
                        } catch (Exception e) {
                            Log.v("LogLogin", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LogLogin", error.getMessage());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("numeroVereador", votoVereador);
                params.put("numeroPrefeito", votoPrefeito);
                params.put("cpfEleitor", cpfEleitor);

                return params;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}