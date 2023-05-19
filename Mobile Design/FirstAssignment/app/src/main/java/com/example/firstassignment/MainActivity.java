package com.example.firstassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toast welcomeToast;
    private static final int toastDuration = 100000;
    private List<Paragraph> paragraphs;
    private String myPhoneNumber = "tel:09380663225";
    private String myEmail = "soheil.mh2000@gmail.com,";
    private LinearLayout paragraphListLayout;
    private ImageButton callButton;
    private ImageButton emailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Parsing Json and showing the paragraphs!
        parseJsonStringToJavaObject("myInfo.json");
        paragraphListLayout = findViewById(R.id.paragraph_list);
        addParagraphsToLayout(paragraphListLayout);
        configureCallAndEmailAndName();
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

    private void configureCallAndEmailAndName() {
        callButton = findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(myPhoneNumber));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });
        emailButton = findViewById(R.id.email_button);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));
                String[] addresses = {myEmail};
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setSelector(selectorIntent);
                startActivity(Intent.createChooser(emailIntent, "Sending Email to..."));
            }
        });


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

    private void addParagraphsToLayout(LinearLayout paragraphListLayout) {
        for (Paragraph paragraph : this.paragraphs) {
            paragraphListLayout.addView(createParagraphView(paragraph));
        }
    }

    private View createParagraphView(Paragraph paragraph){
        View paragraphView = getLayoutInflater().inflate(R.layout.paragraph_layout, null, false);
        TextView titleTextView = (TextView) paragraphView.findViewById(R.id.paragraph_title);
        TextView textTextView = (TextView) paragraphView.findViewById(R.id.paragraph_text);
        titleTextView.setText(paragraph.get_title());
        textTextView.setText(paragraph.get_text());
        return paragraphView;
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