package com.example.dominik.aplikacjawaz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"fonts/XD.TTF");
        TextView myTexview  = (TextView)findViewById(R.id.title);
        TextView myTexview2 = (TextView)findViewById(R.id.set);
        Button ButtonText   = (Button)findViewById(R.id.button1);
        Button ButtonText2  = (Button)findViewById(R.id.button2);
        Button ButtonText3  = (Button)findViewById(R.id.button3);
        Button ButtonText4  = (Button)findViewById(R.id.button5);

        myTexview.setTypeface(myTypeface);
        myTexview2.setTypeface(myTypeface);
        ButtonText.setTypeface(myTypeface);
        ButtonText2.setTypeface(myTypeface);
        ButtonText3.setTypeface(myTypeface);
        ButtonText4.setTypeface(myTypeface);
    }

    public void Lang(View view){
        Button button = (Button) findViewById(R.id.button1);
        String lang = button.getText().toString();

        if (lang=="Language EN") button.setText("Language PL");
        else button.setText("Language EN");
    }

    public void Music(View view){
        Button button = (Button) findViewById(R.id.button2);
        String music = button.getText().toString();

        if (music=="Music ON") button.setText("Music OFF");
        else button.setText("Music ON");
    }


    public void Orient(View v){

        Button button = (Button) findViewById(R.id.button3);
        String orient = button.getText().toString();

        if (orient=="Orient V") button.setText("Orient H");
        else button.setText("Orient V");

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
}
