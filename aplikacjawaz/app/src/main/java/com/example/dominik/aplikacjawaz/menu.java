package com.example.dominik.aplikacjawaz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class menu extends Activity {

    SharedPreferences userPreferences;
    private MediaPlayer mySound;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        mySound = MediaPlayer.create(this, R.raw.kebab);



        //Ustawianie czcionki dla tekstu

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/XD.TTF");
        TextView myTexview = (TextView) findViewById(R.id.title);
        Button ButtonText = (Button) findViewById(R.id.button1);
        Button ButtonText4 = (Button) findViewById(R.id.button4);
        Button ButtonText5 = (Button) findViewById(R.id.button5);

        myTexview.setTypeface(myTypeface);
        ButtonText.setTypeface(myTypeface);
        ButtonText4.setTypeface(myTypeface);
        ButtonText5.setTypeface(myTypeface);

        playmusic();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    public void Start(View view) {
        Intent intent = new Intent(this, gra1.class);
        startActivity(intent);
    }

    public void onSettingsButton(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    public void InstructionsButton(View view) {
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {

        if ((keyCode == KeyEvent.KEYCODE_MENU || keyCode ==  KeyEvent.KEYCODE_BACK) && event.getRepeatCount() == 0);
        return true;
    }

     void playmusic(){
        userPreferences  = getSharedPreferences("settings", 0);

        if (userPreferences.getInt("music", 0) == 1){
            if(mySound.isPlaying());
            else mySound.start();
        }

        if (userPreferences.getInt("music", 0)==0){
            mySound.start();
            mySound.stop();
        }

    }

    public void onPause(){
        super.onPause();
        if (userPreferences.getInt("music", 0) == 1) {
            mySound.pause();
            currentPosition();
        }
    }

    public float currentPosition(){
        return (float)mySound.getCurrentPosition();

    }

    @Override
        protected void onResume() {
        super.onResume();
        if (userPreferences.getInt("music", 0) == 1) mySound.start();
    }
}