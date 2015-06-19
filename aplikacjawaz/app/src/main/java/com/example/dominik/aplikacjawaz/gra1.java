package com.example.dominik.aplikacjawaz;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class gra1 extends Activity {

    private Game game;
    private FrameLayout frameView;
    private TextView score;
    private Activity mActivity;
    SharedPreferences userPreferences, speedSetting;
    private boolean ster=false;
    private int speed;
    private MediaPlayer mySound;



    @Override
    public void onCreate(Bundle savedInstanceState) {



        userPreferences = getSharedPreferences("settings", 0);
        speedSetting = getSharedPreferences("speed", 0);
        if(userPreferences.getInt("controls",0) == 1)  ster = true;
        speed = speedSetting.getInt("speed", 1);
        mySound = MediaPlayer.create(this, R.raw.kebab);
        playmusic();





        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gameplay);
        mActivity = this;


        score = (TextView) findViewById(R.id.score);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"fonts/XD.TTF");
        TextView myTexview  = (TextView)findViewById(R.id.score);
        myTexview.setTypeface(myTypeface);

        game = new Game(this,this,score,ster,speed);
        frameView = (FrameLayout) findViewById(R.id.gameFrame);
        frameView.addView(game);

    }

    public void leftClick(View view){
        game.snake.turnLeft();
    }

    public void rightClick(View view){
        game.snake.turnRight();
    }

    public void downClick(View view){
        game.snake.turnDown();
    }

    public void upClick(View view){
        game.snake.turnUp();
    }

    public void shareOnFacebook(){
        Intent intent = new Intent(this, facebook.class);
        intent.putExtra("facebookMessage", "Wonsz działa.");
        startActivity(intent);
    }


    public void gameOver(){

        final CharSequence[] items = {"Zagraj jeszcze raz","Opublikuj wynik na Facebook","Powrót do menu"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.gameover);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        game.setup();
                        game.invalidate();
                        break;
                    case 1:
                        shareOnFacebook();
                        break;
                    default:
                        mActivity.finish();
                }
            }
        });

        builder.setCancelable(false);
        builder.create().show();
    }


    public void pauseGame(){

        if(game.gameOver) return;

        game.snake.stopped = true;

        final CharSequence[] items = {"Kontynuuj","Zacznij jeszcze raz","Powrót do menu"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.paused);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                switch(item){
                    case 1:
                        game.setup();
                        game.invalidate();

                        break;

                    case 2:
                        mActivity.finish();
                        break;

                    default:
                        game.snake.stopped=false;
                        game.invalidate();
                }
            }
        });

        builder.setCancelable(false);
        builder.create().show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {

        if ((keyCode == KeyEvent.KEYCODE_MENU || keyCode ==  KeyEvent.KEYCODE_BACK) && event.getRepeatCount() == 0)
            pauseGame();

        if((keyCode == KeyEvent.KEYCODE_DPAD_LEFT) && event.getRepeatCount()==0)
            game.snake.turnLeft();

        if((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) && event.getRepeatCount()==0)
            game.snake.turnRight();

        return true;
    }
//muzyka REMOVE KEBAB 8 BIT
    void playmusic() {
        userPreferences = getSharedPreferences("settings", 0);

        if (userPreferences.getInt("music", 0) == 1) {
            if (mySound.isPlaying()) ;
            else mySound.start();
        }

        if (userPreferences.getInt("music", 0) == 0) {
            mySound.start();
            mySound.stop();
        }
    }

    // Pauzowanie
    @Override
    public void onPause(){
        super.onPause();
        if (userPreferences.getInt("music", 0) == 1) mySound.pause();

        pauseGame();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (userPreferences.getInt("music", 0) == 1) mySound.start();
    }


}