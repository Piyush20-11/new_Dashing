package com.example.new_dashing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView ambulanceImageView;
    ImageView cardiView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ambulanceImageView=findViewById(R.id.ambulanceImageView);
        cardiView=findViewById(R.id.cardiView);
        ambulanceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inext=new Intent(MainActivity.this, ambulanceBook.class);
                startActivity(inext);
            }
        });
        cardiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snext=new Intent(MainActivity.this, ambulanceOwner.class);
                startActivity(snext);
            }
        });
    }
}
