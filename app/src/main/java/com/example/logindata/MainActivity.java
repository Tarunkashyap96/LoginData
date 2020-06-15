package com.example.logindata;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class
MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3500;


    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

    topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
    bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

    image.setAnimation(topAnim);
    image.setAnimation(bottomAnim);
    slogan.setAnimation(bottomAnim);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(MainActivity.this,Dashboard.class);

            Pair[]pairs=new Pair[2];pairs[0]=new Pair<View, String>(image,"logo_image");pairs[1]=new Pair<View, String>(logo,"logo_text");
            if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP)
            {
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        }
    },SPLASH_SCREEN);

    }






}