package com.industrialmaster.firstgameapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread newThread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(mainIntent);

                }
            }
        };
        newThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
