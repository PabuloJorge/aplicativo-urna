package com.example.urna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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


public class Vereador extends AppCompatActivity {
    EditText et_dUmVereador, et_dDoisVereador, et_dTresVereador, et_dQuatroVereador, et_dCincoVereador;
    Button bt_brancoVereador, bt_confirmarVereador, bt_corrigeVereador;
    String votoVereador;
    LinearLayout ll_candidatoVereador;
    TextView tv_nomeVereador, tv_partidoVereador, tv_votoBrancoVereador;
    MediaPlayer mp;
    Intent iIniciarVoto;

    RequestQueue requestQueue;
    String url = "https://apiurna.000webhostapp.com/getCandidato.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vereador);

        et_dUmVereador = findViewById(R.id.et_dUmVereador);
        et_dDoisVereador = findViewById(R.id.et_dDoisVereador);
        et_dTresVereador = findViewById(R.id.et_dTresVereador);
        et_dQuatroVereador = findViewById(R.id.et_dQuatroVereador);
        et_dCincoVereador = findViewById(R.id.et_dCincoVereador);

        bt_brancoVereador = findViewById(R.id.bt_brancoVereador);
        bt_corrigeVereador = findViewById(R.id.bt_corrigeVereador);
        bt_confirmarVereador = findViewById(R.id.bt_confirmarVereador);
        bt_confirmarVereador.setEnabled(false);

        ll_candidatoVereador = findViewById(R.id.ll_candidatoVereador);
        ll_candidatoVereador.setVisibility(View.INVISIBLE);

        tv_nomeVereador = findViewById(R.id.tv_nomeVereador);
        tv_partidoVereador = findViewById(R.id.tv_partidoVereador);

        tv_votoBrancoVereador = findViewById(R.id.tv_votoBrancoVereador);
        tv_votoBrancoVereador.setVisibility(View.INVISIBLE);

        mp = MediaPlayer.create(Vereador.this, R.raw.votando_vereador);
        mp.start();

        iIniciarVoto = getIntent();

        requestQueue = Volley.newRequestQueue(this);

        bt_corrigeVereador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                et_dUmVereador.setText("");
                et_dDoisVereador.setText("");
                et_dTresVereador.setText("");
                et_dQuatroVereador.setText("");
                et_dCincoVereador.setText("");

                et_dDoisVereador.setVisibility(View.INVISIBLE);
                et_dTresVereador.setVisibility(View.INVISIBLE);
                et_dQuatroVereador.setVisibility(View.INVISIBLE);
                et_dCincoVereador.setVisibility(View.INVISIBLE);

                et_dUmVereador.setEnabled(true);
                et_dDoisVereador.setEnabled(true);
                et_dTresVereador.setEnabled(true);
                et_dQuatroVereador.setEnabled(true);
                et_dCincoVereador.setEnabled(true);

                et_dUmVereador.setVisibility(View.VISIBLE);
                et_dUmVereador.requestFocus();

                tv_nomeVereador.setText("");
                tv_partidoVereador.setText("");

                ll_candidatoVereador.setVisibility(View.INVISIBLE);
                tv_votoBrancoVereador.setVisibility(View.INVISIBLE);
                bt_confirmarVereador.setEnabled(false);
                bt_brancoVereador.setEnabled(true);


            }
        });

        et_dDoisVereador.setVisibility(View.INVISIBLE);
        et_dTresVereador.setVisibility(View.INVISIBLE);
        et_dQuatroVereador.setVisibility(View.INVISIBLE);
        et_dCincoVereador.setVisibility(View.INVISIBLE);


        et_dUmVereador.requestFocus();
        et_dUmVereador.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // Perform action on key press
                            if (et_dUmVereador.getText().toString().length() == 1) {
                                et_dDoisVereador.setVisibility(View.VISIBLE);
                                et_dDoisVereador.requestFocus();
                                et_dUmVereador.setEnabled(false);
                                bt_brancoVereador.setEnabled(false);
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        et_dDoisVereador.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // Perform action on key press
                            if (et_dDoisVereador.getText().toString().length() == 1) {
                                et_dTresVereador.setVisibility(View.VISIBLE);
                                et_dTresVereador.requestFocus();
                                et_dDoisVereador.setEnabled(false);
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        et_dTresVereador.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // Perform action on key press
                            if (et_dTresVereador.getText().toString().length() == 1) {
                                et_dQuatroVereador.setVisibility(View.VISIBLE);
                                et_dQuatroVereador.requestFocus();
                                et_dTresVereador.setEnabled(false);
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        et_dQuatroVereador.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // Perform action on key press
                            if (et_dQuatroVereador.getText().toString().length() == 1) {
                                et_dCincoVereador.setVisibility(View.VISIBLE);
                                et_dCincoVereador.requestFocus();
                                et_dQuatroVereador.setEnabled(false);
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        et_dCincoVereador.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            // Perform action on key press
                            if (et_dCincoVereador.getText().toString().length() == 1) {
                                et_dCincoVereador.setEnabled(false);
                                votoVereador = et_dUmVereador.getText().toString() + et_dDoisVereador.getText().toString() + et_dTresVereador.getText().toString() + et_dQuatroVereador.getText().toString() + et_dCincoVereador.getText().toString();
                                getCandidato();
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        bt_brancoVereador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                votoVereador = "BRANCO";
                et_dUmVereador.setVisibility(View.INVISIBLE);
                tv_votoBrancoVereador.setText("VOTO BRANCO");
                tv_votoBrancoVereador.setVisibility(View.VISIBLE);
                bt_confirmarVereador.setEnabled(true);
                mp.stop();
                mp = MediaPlayer.create(Vereador.this, R.raw.voto_branco);
                mp.start();
            }
        });

        bt_confirmarVereador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp = MediaPlayer.create(Vereador.this, R.raw.fim_voto);
                mp.start();
                Intent i = new Intent(Vereador.this, Prefeito.class);
                i.putExtra("votoVereador", votoVereador);
                i.putExtra("cpfEleitor", iIniciarVoto.getStringExtra("cpfEleitor"));
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
                                votoVereador = "NULO";
                                et_dUmVereador.setVisibility(View.INVISIBLE);
                                et_dDoisVereador.setVisibility(View.INVISIBLE);
                                et_dTresVereador.setVisibility(View.INVISIBLE);
                                et_dQuatroVereador.setVisibility(View.INVISIBLE);
                                et_dCincoVereador.setVisibility(View.INVISIBLE);
                                tv_votoBrancoVereador.setText("VOTO NULO");
                                tv_votoBrancoVereador.setVisibility(View.VISIBLE);
                                bt_confirmarVereador.setEnabled(true);
                                mp.stop();
                                mp = MediaPlayer.create(Vereador.this, R.raw.voto_nulo_selecionado);
                                mp.start();
                            }else{
                                tv_nomeVereador.setText(jsonObject.getString("nome"));
                                tv_partidoVereador.setText(jsonObject.getString("partido"));
                                ll_candidatoVereador.setVisibility(View.VISIBLE);
                                bt_confirmarVereador.setEnabled(true);
                                mp.stop();
                                mp = MediaPlayer.create(Vereador.this, R.raw.vereador_localizado);
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
                params.put("numeroCandidato", votoVereador);

                return params;
            }
        };
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}