package com.example.firstassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toast welcomeToast;
    private static final int toastDuration = 100000;
    private List<Paragraph> paragraphs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Parsing Json and showing the paragraphs!
        parseJsonStringToJavaObject("myInfo.json");

        // Showing Toast!
//        welcomeToast = Toast.makeText(MainActivity.this, "Welcome to my app!", Toast.LENGTH_LONG);
//        CountDownTimer toastCountDown = new CountDownTimer(15000, 1500) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                welcomeToast.show();
//            }
//
//            @Override
//            public void onFinish() {
//               welcomeToast.cancel();
//            }
//        };
//
//        welcomeToast.show();
//        //toastCountDown.start();
//        Runnable r = new ToastThread(welcomeToast);
//        new Thread(r).start();
//        this.welcomeToast.show();

    }

    private void parseJsonStringToJavaObject(String assetJsonFileName){
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), assetJsonFileName);
        Gson gson = new Gson();
        Type paragraphType = new TypeToken<List<Paragraph>>(){}.getType();
        this.paragraphs = gson.fromJson(jsonFileString, paragraphType);
        for (Paragraph paragraph : this.paragraphs) {
            System.out.println(paragraph.get_text());
        }
    }

    private class ToastThread implements Runnable{
        private Toast toast;
        public ToastThread(Toast toast){
            this.toast = toast;
        }
        public void run() {
            // show toast for 10 seconds
            for (int i = 0;i < 10;i++) {
                this.toast.show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.toast.cancel();
                //System.out.println("Im here" + i);

            }

        }
    }
}