package com.example.dominik.aplikacjawaz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class Instructions extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_instructions);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"fonts/XD.TTF");
        TextView myTexview  = (TextView)findViewById(R.id.title);
        TextView myTexview2 = (TextView)findViewById(R.id.instr);
        TextView myTexview3 = (TextView)findViewById(R.id.instrukt);
        Button button = (Button)findViewById(R.id.btm);

        myTexview.setTypeface(myTypeface);
        myTexview2.setTypeface(myTypeface);
        myTexview3.setTypeface(myTypeface);
        button.setTypeface(myTypeface);

    }

    public void BTM(View view) {
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
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if ((keyCode == KeyEvent.KEYCODE_MENU || keyCode ==  KeyEvent.KEYCODE_BACK) && event.getRepeatCount() == 0);
        return true;
    }
}
