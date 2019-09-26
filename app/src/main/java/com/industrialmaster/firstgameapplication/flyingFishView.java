package com.industrialmaster.firstgameapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth,canvasHeight;

    private int yellowX,yellowY,yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int greenX,greenY,greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX,redY,redSpeed = 25;
    private Paint redPaint = new Paint();

    private int score,lifeCounteroffish;

    boolean touch = false;
    private Bitmap backgroindImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public flyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroindImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY=450;
        score=0;
        lifeCounteroffish = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroindImage,0,0,null);
        int minfishY = fish[0].getHeight();
        int maxfishY = canvasHeight - fish[0].getHeight() * 1;
        fishY = fishY + fishSpeed;
        if(fishY<minfishY){
            fishY=minfishY;
        }
        if(fishY>maxfishY){
            fishY=maxfishY;
        }

        fishSpeed = fishSpeed + 2;
        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch=false;
        }else{
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        yellowX = yellowX - yellowSpeed;

        if(hitBallChecker(yellowX,yellowY)){
            score = score + 10;
            yellowX = -100;
        }

        if(yellowX < 0){
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxfishY - minfishY)) + minfishY;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);

        greenX = greenX - greenSpeed;

        if(hitBallChecker(greenX,greenY)){
            score = score + 20;
            greenX = -100;
        }

        if(greenX < 0){
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxfishY - minfishY)) + minfishY;
        }
        canvas.drawCircle(greenX,greenY,18,greenPaint);


        redX = redX - redSpeed;

        if(hitBallChecker(redX,redY)){
            //score = score + 20;
            redX = -100;
            lifeCounteroffish--;
            if(lifeCounteroffish==0){
                Toast.makeText(getContext(),"Game over",Toast.LENGTH_SHORT).show();
                Intent gameOverintent = new Intent(getContext(),GameOverActivity.class);
                gameOverintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverintent.putExtra("Score",score);
                getContext().startActivity(gameOverintent);
            }
        }

        if(redX < 0){
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxfishY - minfishY)) + minfishY;
        }
        canvas.drawCircle(redX,redY,30,redPaint);

        canvas.drawText("Score : " + score,20,60,scorePaint);
        for (int i=0; i<3;i++){
            int x = (int) (445 + life[0].getWidth() * 1.5 * i);
            int y = 30;
            if (i < lifeCounteroffish){
                canvas.drawBitmap(life[0], x, y,null);
            }else{
                canvas.drawBitmap(life[1], x, y,null);
            }
        }
    }

    public boolean hitBallChecker(int x,int y){
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())){
            return true;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            fishSpeed = -30;
        }
        return true;
    }
}
