package com.example.dominik.aplikacjawaz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class settings extends Activity {


    SharedPreferences userPreferences;
    SharedPreferences.Editor userPreferencesEditor;
    int musicc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        userPreferences  = getSharedPreferences("settings", 0);

        //zmiana czcionki
        changeFont();

        Button button = (Button) findViewById(R.id.sbutton2);
        int musica = userPreferences.getInt("music", 0);

        if (musica == 1){
            button.setText(R.string.musicon);

        }
        if (musica==0){
            button.setText(R.string.musicoff);}

    }


    public void Music(View view) {
        Button button = (Button) findViewById(R.id.sbutton2);
        int musicac = userPreferences.getInt("music",0);

        if (musicac == 1){
            button.setText(R.string.musicoff);
            musicc=0;
        }
        if (musicac==0){
            button.setText(R.string.musicon);
            musicc=1;
        }
        userPreferencesEditor = userPreferences.edit();
        userPreferencesEditor.putInt("music", musicc);
        userPreferencesEditor.commit();
    }


    public void BackToMenu(View view) {
        Intent intent = new Intent(this, menu.class);
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


    public void changeFont() {
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/XD.TTF");
        TextView myTexview = (TextView) findViewById(R.id.title);
        TextView myTexview2 = (TextView) findViewById(R.id.set);
        Button ButtonText2 = (Button) findViewById(R.id.sbutton2);
        Button ButtonText4 = (Button) findViewById(R.id.sbutton5);

        myTexview.setTypeface(myTypeface);
        myTexview2.setTypeface(myTypeface);
        ButtonText2.setTypeface(myTypeface);
        ButtonText4.setTypeface(myTypeface);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {

        // On Menu or Back Press, Pause Game
        if ((keyCode == KeyEvent.KEYCODE_MENU || keyCode ==  KeyEvent.KEYCODE_BACK) && event.getRepeatCount() == 0);
        return true;
    }
}