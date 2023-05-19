package edu.sharif.myintroductionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

class FirstTime{
    public static boolean isFirstTimeOpeningApp = true;
}
public class MainActivity extends AppCompatActivity {

    private Toast welcomeToast;
    private static final int toastDuration = 10000;
    private List<Paragraph> paragraphs;
    private String myPhoneNumber = "tel:09380663225";
    private String myEmail = "soheil.mh2000@gmail.com,";
    private String myName = "Soheil Mahdizadeh Zoolpirani";
    private LinearLayout paragraphListLayout;
    private ImageButton callButton;
    private ImageButton emailButton;
    private SwitchMaterial switchMaterial;
    private TextView myNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Showing Toast if first time!
        if (FirstTime.isFirstTimeOpeningApp) {
            showToast();
            FirstTime.isFirstTimeOpeningApp = false;
        }
        setContentView(R.layout.activity_main);
        // Updating nyNameText with myName
        myNameText = findViewById(R.id.nameTextView);
        myNameText.setText(myName);

        // Parsing Json and showing the paragraphs!
        parseJsonStringToJavaObject("myInfo.json");
        paragraphListLayout = findViewById(R.id.paragraph_list);
        addParagraphsToLayout(paragraphListLayout);
        configureCallAndEmailAndName();


        // Configuring Night/Day switch button!
        switchMaterial = findViewById(R.id.switchTheme);
        configureNightAndDaySwitchButton(switchMaterial);

        // Setting the current Theme!
        setSwitchText(switchMaterial);

    }

    private void showToast() {
        welcomeToast = Toast.makeText(MainActivity.this, "Welcome to my app!", Toast.LENGTH_LONG);
        CountDownTimer toastCountDown = new CountDownTimer(toastDuration, 4100) {
            @Override
            public void onTick(long millisUntilFinished) {
                welcomeToast.show();
            }

            @Override
            public void onFinish() {
               welcomeToast.cancel();
            }
        };

        welcomeToast.show();
        toastCountDown.start();
    }

    private void setSwitchText(SwitchMaterial switchMaterial) {
        boolean isNightModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        switchMaterial.setChecked(isNightModeOn);
        if (isNightModeOn) {
            switchMaterial.setText("Night Mode ");
        } else {
            switchMaterial.setText("Light Mode ");
        }
    }

    private void configureNightAndDaySwitchButton(SwitchMaterial switchMaterial){
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    buttonView.setText("Night Mode");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
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
        titleTextView.setTypeface(titleTextView.getTypeface(), Typeface.BOLD);
        TextView textTextView = (TextView) paragraphView.findViewById(R.id.paragraph_text);
        titleTextView.setText(paragraph.get_title());
        textTextView.setText(paragraph.get_text());
        return paragraphView;
    }

}