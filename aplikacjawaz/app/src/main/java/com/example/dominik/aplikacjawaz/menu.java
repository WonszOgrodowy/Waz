package com.example.dominik.aplikacjawaz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class menu extends Activity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        //Ustawianie czcionki dla tekstu

        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"fonts/XD.TTF");
        TextView myTexview  = (TextView)findViewById(R.id.title);
        Button ButtonText   = (Button)findViewById(R.id.button1);
        Button ButtonText2  = (Button)findViewById(R.id.button2);
        Button ButtonText3  = (Button)findViewById(R.id.button3);
        Button ButtonText4  = (Button)findViewById(R.id.button4);
        Button ButtonText5  = (Button)findViewById(R.id.button5);

        myTexview.setTypeface(myTypeface);
        ButtonText.setTypeface(myTypeface);
        ButtonText2.setTypeface(myTypeface);
        ButtonText3.setTypeface(myTypeface);
        ButtonText4.setTypeface(myTypeface);
        ButtonText5.setTypeface(myTypeface);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    public void Start(View view){
        Intent intent = new Intent(this, gameplay.class);
        startActivity(intent);
    }

    public void HighScoreButton(View view){
        Intent intent = new Intent(this, HighScores.class);
        startActivity(intent);
    }


    public void onSettingsButton(View view){
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    public void InstructionsButton(View view){
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
/*    public void OpenSettings(View view){
        setContentView(R.layout.activity_settings);
    }*/
}
