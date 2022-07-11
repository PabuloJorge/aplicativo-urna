package com.example.urna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    EditText et_cpf;
    Button bt_confirmarEleitor, bt_tutorial;
    String cpf;
    MediaPlayer mp;
    ProgressBar pb_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(MainActivity.this, R.raw.bem_vindo);
        mp.start();

        et_cpf = findViewById(R.id.et_cpf);
        bt_confirmarEleitor = findViewById(R.id.bt_confirmarEleitor);

        pb_login = findViewById(R.id.pb_login);

        bt_tutorial = findViewById(R.id.bt_tutorial);

        bt_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                Intent i = new Intent(MainActivity.this, Tutorial.class);
                startActivity(i);
            }
        });

        bt_confirmarEleitor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mp.stop();
                boolean validado = true;

                cpf = et_cpf.getText().toString().trim();

                if (cpf.isEmpty()) {
                    et_cpf.setError("Campo obrigatório");
                    et_cpf.requestFocus();
                    validado = false;
                    mp = MediaPlayer.create(MainActivity.this, R.raw.cpf_vazio);
                    mp.start();
                } else if (cpf.length() < 11) {
                    et_cpf.setError("CPF inválido");
                    et_cpf.requestFocus();
                    validado = false;
                    mp = MediaPlayer.create(MainActivity.this, R.raw.cpf_invalido);
                    mp.start();
                }

                if (validado) {
                    mp.stop();
                    pb_login.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this, IniciarVoto.class);
                            i.putExtra("cpfEleitor", cpf);
                            startActivity(i);
                            finish();
                        }
                    }, 3000);


                }

            }
        });


    }
}