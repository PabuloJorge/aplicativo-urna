package com.example.urna;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        VideoView vv_video = findViewById(R.id.vv_video);

        String videoPath= "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri=Uri.parse(videoPath);
        vv_video.setVideoURI(uri);

        MediaController mediaController=new MediaController(this);
        vv_video.setMediaController(mediaController);
        mediaController.setAnchorView(vv_video);
    }
}