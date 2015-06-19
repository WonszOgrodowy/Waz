package com.example.dominik.aplikacjawaz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends View {

    private boolean setupComplete = false;
    private int pxSquare, squaresWidth, squaresHeight,sqBorder=0,padding=0;
    private ArrayList<Block> walls;
    public Snake snake;
    private Food food;
    private Random random;
    private TextView scoreView;
    private gra1 mActivity;
    public boolean gameOver=false;
    private int score,frameRate;
    private boolean ster;
/*    private MediaPlayer mySound;
    SharedPreferences userPreferences;*/


    public Game(Context context,gra1 activity,TextView scoreView,boolean ster,int speed) {
        super(context);
        mActivity = activity;
        random = new Random();
        this.scoreView = scoreView;
        this.ster = ster;
        this.frameRate = 5*(speed+1);



    }

    // If User Scores
    private void score(){
        score++;
        scoreView.setText(Integer.toString(this.score));
    }

    protected void onDraw(Canvas canvas){
        if(!setupComplete) {
            setup();
            this.invalidate();
            return;
        }

        for(Block block:walls){
            block.draw(canvas);
        }

        snake.draw(canvas);

        food.draw(canvas);


        final View parent = this;
        if(!snake.stopped){
            new Thread(new Runnable() {
                public void run() {
                    parent.postDelayed(new Runnable() {
                        public void run() {
                            parent.invalidate();
                        }
                    },1000/frameRate);
                }
            }).start();
        }else if(gameOver){
            new Thread(new Runnable() {
                public void run() {
                    parent.postDelayed(new Runnable() {
                        public void run() {
                            mActivity.gameOver();
                        }
                    },500);
                }
            }).start();
        }
    }

    public void setup(){

        score = -1;
        this.score();
        gameOver=false;

        int pxWidth = getWidth();
        int pxHeight = getHeight();
        int dpi = getResources().getDisplayMetrics().densityDpi;
        float inWidth = ((float) pxWidth) / dpi;
        float inHeight = ((float) pxHeight) / dpi;

        squaresWidth  = (int) (inWidth * 10.0);
        squaresHeight = (int) (inHeight * 10.0);
        if(squaresHeight < 15) squaresHeight = 15;
        if(squaresWidth < 15)  squaresWidth = 15;

        int pxSquareWidth = pxWidth / squaresWidth;
        int pxSquareHeight = pxHeight / squaresHeight;
        if(pxSquareWidth > pxSquareHeight)
            pxSquare = pxSquareHeight;
        else
            pxSquare = pxSquareWidth;
        padding = (pxWidth - squaresWidth * pxSquare)/2;
        sqBorder = pxSquare / 20;

        walls = new ArrayList<Block>();
        for(int j=0;j<squaresWidth;j++){
            walls.add(new Block(j,0,0));  //gora sciana
            walls.add(new Block(j,squaresHeight-2,0));  //dol sciana
        }for(int j=1;j<(squaresHeight-1);j++){ //lewa sciana
            walls.add(new Block(0,j,0));  //lewa sciana
            walls.add(new Block(squaresWidth-1,j,0)); //prawa sciana
        }

        snake = new Snake();

        food = new Food(snake,walls);

        setupComplete = true;
    }


    public class Snake{

        public ArrayList<Block> blocks;
        private int direction,length;
        public boolean stopped=false;

        public Snake(){

            blocks = new ArrayList<Block>();
            blocks.add(new Block(squaresWidth/2,squaresHeight/2,1));
            length=3;

            direction = random.nextInt(4);
            switch(direction){
                case 0: //prawo
                    blocks.add(new Block(squaresWidth/2-1,squaresHeight/2,1));
                    blocks.add(new Block(squaresWidth/2-2,squaresHeight/2,1));
                    break;
                case 1: //dol
                    blocks.add(new Block(squaresWidth/2,squaresHeight/2-1,1));
                    blocks.add(new Block(squaresWidth/2,squaresHeight/2-2,1));
                    break;
                case 2: //lewo
                    blocks.add(new Block(squaresWidth/2+1,squaresHeight/2,1));
                    blocks.add(new Block(squaresWidth/2+2,squaresHeight/2,1));
                    break;
                case 3: //prawo
                    blocks.add(new Block(squaresWidth/2,squaresHeight/2+1,1));
                    blocks.add(new Block(squaresWidth/2,squaresHeight/2+2,1));
            }
        }


        public void draw(Canvas canvas){
            if(!stopped) move();
            for(Block block:blocks) block.draw(canvas);
        }


        public void turnLeft(){
            if(ster){
                this.direction -= 1;
                if(this.direction < 0) this.direction = 3;
            }else if(this.direction != 0 && this.direction != 2)
                this.direction = 2;
        }


        public void turnRight(){
            if(ster){
                this.direction += 1;
                if(this.direction > 3) this.direction = 0;
            }else if(this.direction != 0 && this.direction != 2)
                this.direction = 0;
        }


        public void turnDown(){
            if(!ster && this.direction != 1 && this.direction != 3)
                this.direction = 1;
        }


        public void turnUp(){
            if(!ster && this.direction != 1 && this.direction != 3)
                this.direction = 3;
        }


        public void move(){


            Block frontBlock = blocks.get(0);


            Block newBlock;
            switch(direction){
                case 0: //w prawo
                    newBlock = new Block(frontBlock.x+1,frontBlock.y,1);
                    break;
                case 1: //w dol
                    newBlock = new Block(frontBlock.x,frontBlock.y+1,1);
                    break;
                case 2: //w lewo
                    newBlock = new Block(frontBlock.x-1,frontBlock.y,1);
                    break;
                default:  //w prawo
                    newBlock = new Block(frontBlock.x,frontBlock.y-1,1);
            }

            // kolizja ze sciana
            if(this.collides(newBlock) || newBlock.collides(walls)){
                stopped = true;
                for(Block block:blocks){
                    block.setType(3);
                }
                newBlock.setType(0);
                gameOver=true;

                // jesli idzie spoko
            }else{


                blocks.add(0,newBlock);

                // kolizja z punktem
                if(this.collides(food)){
                    food.move(this, walls);
                    length++;
                    score();
             //       musicPoint();

                    // jak idzie spoko to zeby usuwal ostatni blok bo rysuje kolejny dalej
                }else
                    blocks.remove(length);
            }
        }

        // sprawdzanie kolizji
        public boolean collides(Block block){
            for(Block oneBlock:this.blocks)
                if(block.collides(oneBlock)) return true;
            return false;
        }

    }

    public class Block {
        public int x=0,y=0;
        ShapeDrawable shape;

        public Block(){}

        public Block(int x,int y,int type){
            this.x = x;
            this.y = y;

            shape = new ShapeDrawable(new RectShape());
            shape.setBounds(padding+x*pxSquare+sqBorder,padding+y*pxSquare+sqBorder,padding+(x+1)*pxSquare-sqBorder,padding+(y+1)*pxSquare-sqBorder);

            this.setType(type);
        }

        public void draw(Canvas canvas){
            shape.draw(canvas);
        }

        public boolean collides(Block block){
            return block.x == this.x && block.y == this.y;
        }

        public boolean collides(ArrayList<Block> blocks){
            for(Block block:blocks){
                if(this.collides(block)) return true;
            }
            return false;
        }

        public void setType(int type){
            switch(type){
                case 0: //sciany czarne
                    shape.getPaint().setColor(Color.BLACK);
                    break;
                case 1: //wonsz czarny
                    shape.getPaint().setColor(Color.BLACK);
                    break;
                case 2: //punkty szare
                    shape.getPaint().setColor(Color.GRAY);
                    break;
                case 3: //jak kolizja ze sciana to koloruje na czerwono
                    shape.getPaint().setColor(Color.RED);
            }
        }

    }

    class Food extends Block {

        public Food(Snake snake, ArrayList<Block> blocks){
            shape = new ShapeDrawable(new RectShape());
            this.setType(2);
            this.move(snake,blocks);
        }

        public void move(Snake snake, ArrayList<Block> blocks){
            while(true){
                this.x = random.nextInt(squaresWidth-3)+1;
                this.y = random.nextInt(squaresHeight-3)+1;
                if(!snake.collides(this) && !this.collides(blocks)) break;
            }
            shape.setBounds(padding+x*pxSquare+sqBorder,padding+y*pxSquare+sqBorder,padding+(x+1)*pxSquare-sqBorder,padding+(y+1)*pxSquare-sqBorder);
        }

    }

/*    void musicPoint(){
        userPreferences  = getSharedPreferences("settings", 0);
        mySound = MediaPlayer.create(this, R.raw.point);
        if (userPreferences.getInt("music", 0) == 1) mySound.start();
    }*/



}