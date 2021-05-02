package com.example.motionsensors;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    TextView songText;
    MediaPlayer player1, player2, player3, player4;

    float playbackSpeed = 1.5f;
    private SoundPool soundPool;
    boolean songChange = false;
    boolean song1Playing, song2Playing, song3Playing, song4Playing = false;


    int loopChange = 0;

    int song1 = R.raw.chrisbrown;
    int song2 = R.raw.roxanne;
    int song3 = R.raw.rasputin;
    int song4 = R.raw.bruno;
    int loopCount = 0;


    private Accelerometer accelerometer;
    private Gyroscope gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();

            soundPool = new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build();
        }
        else{
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        }



        int sound1 = soundPool.load(this, R.raw.chrisbrown, 1);
        int sound2 = soundPool.load(this, R.raw.roxanne, 1);
        int sound3 = soundPool.load(this, R.raw.rasputin, 1);
        int sound4 = soundPool.load(this, R.raw.bruno, 1);

        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);

        player1 = MediaPlayer.create(MainActivity.this, song1);
        player2 = MediaPlayer.create(MainActivity.this, song2);
        player3 = MediaPlayer.create(MainActivity.this, song3);
        player4 = MediaPlayer.create(MainActivity.this, song4);

        songText = findViewById(R.id.songText);




        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {


                if(tx > 1.0 && tx <= 8.0){

                    pauseAll();
                    getWindow().getDecorView().setBackgroundColor(Color.RED);


                    //soundPool.play(sound1, 1, 1, 1, 0, 1);
                    player1.start();

                    song1Playing = true;
                    loopCount++;
                    songText.setText("Forever - Chris Brown");


                }

                else if(tx > 8.0){
                    pauseAll();
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);


                    //soundPool.play(sound3, 1, 1, 1, 0, 1);
                    player3.start();
                    song3Playing = true;
                    loopCount++;
                    songText.setText("Rasputin - Boney M");


                }


                else if (tx < -1.0 && tx >= -8.0){
                    pauseAll();
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);


                    //soundPool.play(sound2, 1, 1, 1, 0, 1);
                    player2.start();
                    song2Playing = true;
                    loopCount++;
                    songText.setText("Roxanne - The Police");





                }

                else if(tx < -8.0){
                    pauseAll();
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);



                    //soundPool.play(sound4, 1, 1, 1, 0, 1);
                    player4.start();
                    song4Playing = true;
                    loopCount++;
                    songText.setText("Locked out of Heaven - Bruno Mars");



                }
                else{
                    
                    pauseAll();
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    songText.setText(" ");

                }


            }
        });

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(rz > 5.0){
                    pauseAll();
                    //getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                    if(loopCount == 0){
                        player3.start();
                        loopCount++;
                    }


                }
                else if (rz < -5.0){
                    pauseAll();
                    //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);

                    if(loopCount == 0){
                        player4.start();
                        loopCount++;
                    }



                }


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.register();
        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.unregister();
        gyroscope.unregister();
    }



    public void pauseAll(){
        loopCount = 0;
        if(player1.isPlaying()){
            player1.pause();


        }
        if(player2.isPlaying()){
            player2.pause();
        }
        if(player3.isPlaying()){
            player3.pause();
        }
        if(player4.isPlaying()){
            player4.pause();
        }





    }

    public void stopAll(){
        player1.stop();
        player2.stop();
        player3.stop();
        player4.stop();





    }


}