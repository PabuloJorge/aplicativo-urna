package com.example.urna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class IniciarVoto extends AppCompatActivity {
 Button bt_iniciarVoto;
 Intent iMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_voto);

        MediaPlayer mp = MediaPlayer.create(IniciarVoto.this, R.raw.iniciar_voto);
        mp.start();

        bt_iniciarVoto = findViewById(R.id.bt_iniciarVoto);

        iMain = getIntent();

        bt_iniciarVoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                Intent i = new Intent(IniciarVoto.this, Vereador.class);
                i.putExtra("cpfEleitor", iMain.getStringExtra("cpfEleitor"));
                startActivity(i);
                finish();
            }
        });


    }
}