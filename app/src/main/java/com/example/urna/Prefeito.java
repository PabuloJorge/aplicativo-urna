package com.example.urna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Prefeito extends AppCompatActivity {

    EditText et_dUmPrefeito, et_dDoisPrefeito;
    Button bt_brancoPrefeito, bt_confirmarPrefeito, bt_corrigePrefeito;
    String votoPrefeito;
    LinearLayout ll_candidatoPrefeito;
    TextView tv_nomePrefeito, tv_partidoPrefeito, tv_votoBrancoPrefeito;
    MediaPlayer mp;
    Intent iVereador;

    RequestQueue requestQueue;
    String url = "https://apiurna.000webhostapp.com/getCandidato.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefeito);

        et_dUmPrefeito = findViewById(R.id.et_dUmPrefeito);
        et_dDoisPrefeito = findViewById(R.id.et_dDoisPrefeito);


        bt_brancoPrefeito = findViewById(R.id.bt_brancoPrefeito);
        bt_corrigePrefeito = findViewById(R.id.bt_corrigePrefeito);
        bt_confirmarPrefeito = findViewById(R.id.bt_confirmarPrefeito);
        bt_confirmarPrefeito.setEnabled(false);

        ll_candidatoPrefeito = findViewById(R.id.ll_candidatoPrefeito);
        ll_candidatoPrefeito.setVisibility(View.INVISIBLE);

        tv_nomePrefeito = findViewById(R.id.tv_nomePrefeito);
        tv_partidoPrefeito = findViewById(R.id.tv_partidoPrefeito);

        tv_votoBrancoPrefeito = findViewById(R.id.tv_votoBrancoPrefeito);
        tv_votoBrancoPrefeito.setVisibility(View.INVISIBLE);

        mp = MediaPlayer.create(Prefeito.this, R.raw.votando_prefeito);
        mp.start();

        iVereador = getIntent();

        requestQueue = Volley.newRequestQueue(this);

        bt_corrigePrefeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_dUmPrefeito.setText("");
                et_dDoisPrefeito.setText("");


                et_dDoisPrefeito.setVisibility(View.INVISIBLE);


                et_dUmPrefeito.setEnabled(true);
                et_dDoisPrefeito.setEnabled(true);

                et_dUmPrefeito.setVisibility(View.VISIBLE);
                et_dUmPrefeito.requestFocus();

                tv_nomePrefeito.setText("");
                tv_partidoPrefeito.setText("");

                ll_candidatoPrefeito.setVisibility(View.INVISIBLE);
                tv_votoBrancoPrefeito.setVisibility(View.INVISIBLE);
                bt_confirmarPrefeito.setEnabled(false);
                bt_brancoPrefeito.setEnabled(true);



            }
        });

        et_dDoisPrefeito.setVisibility(View.INVISIBLE);



        et_dUmPrefeito.requestFocus();
        et_dUmPrefeito.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // Perform action on key press
                            if (et_dUmPrefeito.getText().toString().length() == 1) {
                                et_dDoisPrefeito.setVisibility(View.VISIBLE);
                                et_dDoisPrefeito.requestFocus();
                                et_dUmPrefeito.setEnabled(false);
                                bt_brancoPrefeito.setEnabled(false);
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        et_dDoisPrefeito.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // Perform action on key press
                            if (et_dDoisPrefeito.getText().toString().length() == 1) {
                                et_dDoisPrefeito.setEnabled(false);
                                votoPrefeito = et_dUmPrefeito.getText().toString() + et_dDoisPrefeito.getText().toString();
                                getCandidato();
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );


        bt_brancoPrefeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                votoPrefeito = "BRANCO";
                et_dUmPrefeito.setVisibility(View.INVISIBLE);
                tv_votoBrancoPrefeito.setText("VOTO BRANCO");
                tv_votoBrancoPrefeito.setVisibility(View.VISIBLE);
                bt_confirmarPrefeito.setEnabled(true);
                mp.stop();
                mp = MediaPlayer.create(Prefeito.this, R.raw.voto_branco);
                mp.start();
            }
        });

        bt_confirmarPrefeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp = MediaPlayer.create(Prefeito.this, R.raw.fim_voto);
                mp.start();
                Intent i = new Intent(Prefeito.this, FimVotacao.class);
                i.putExtra("votoPrefeito", votoPrefeito);
                i.putExtra("votoVereador",iVereador.getStringExtra("votoVereador"));
                i.putExtra("cpfEleitor", iVereador.getStringExtra("cpfEleitor"));
                startActivity(i);
                finish();
            }
        });
    }
    private void getCandidato() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean isErro = jsonObject.getBoolean("erro");

                            if (isErro){
                                votoPrefeito = "NULO";
                                et_dUmPrefeito.setVisibility(View.INVISIBLE);
                                et_dDoisPrefeito.setVisibility(View.INVISIBLE);
                                tv_votoBrancoPrefeito.setText("VOTO NULO");
                                tv_votoBrancoPrefeito.setVisibility(View.VISIBLE);
                                bt_confirmarPrefeito.setEnabled(true);
                                mp.stop();
                                mp = MediaPlayer.create(Prefeito.this, R.raw.voto_nulo_selecionado);
                                mp.start();
                            }else{
                                tv_nomePrefeito.setText(jsonObject.getString("nome"));
                                tv_partidoPrefeito.setText(jsonObject.getString("partido"));
                                ll_candidatoPrefeito.setVisibility(View.VISIBLE);
                                bt_confirmarPrefeito.setEnabled(true);
                                mp.stop();
                                mp = MediaPlayer.create(Prefeito.this, R.raw.prefeito_localizado);
                                mp.start();
                            }
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
                params.put("numeroCandidato", votoPrefeito);

                return params;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}