package com.xclusive.xclusive_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.format;

public class bmi_activity extends AppCompatActivity {
     private Button done;
     private ImageView back;
     private TextView result;
     private EditText height,weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_activity);
        init();
        btnclick();
    }

    private void btnclick() {
        back.setOnClickListener(v->{
            finish();
            onBackPressed();
        });

        done.setOnClickListener(v->{

            if(height.getText().toString().isEmpty() && weight.getText().toString().isEmpty()){
                Toast.makeText(bmi_activity.this, "Both Height and Weight are Mandatory.", Toast.LENGTH_SHORT).show();
            }
            else{

                float height1 = Float.parseFloat(height.getText().toString());
                float weight1  =  Float.parseFloat(weight.getText().toString());
                float heightinm = (float) (height1 * 0.3048);// converting in meter from feet
                float res = weight1/(heightinm*heightinm);


                if(res<18.5){
                    result.setText("Result: "+"Underweight "+ format("%.2f",res));
                    result.setBackgroundColor(Color.parseColor("#00658F"));

                }
                if(res>18.5 && res<24.9){
                    result.setText("Result: "+"Healthy "+ format("%.2f",res));
                    result.setBackgroundColor(Color.parseColor("#63C1C6"));

                }
                if(res>24.9 && res<29.9){
                    result.setText("Result: "+"Overweight "+ format("%.2f",res));
                    result.setBackgroundColor(Color.parseColor("#F5AD84"));

                }
                if(res>30 && res<34.9){
                    result.setText("Result: "+"obesity "+ format("%.2f",res));
                    result.setBackgroundColor(Color.parseColor("#ED7632"));

                }
                if(res>35){
                    result.setText("Result: "+"severe obesity "+ format("%.2f",res));
                    result.setBackgroundColor(Color.parseColor("#CB414C"));


                }
            }
        });
    }

    private void init() {

        back = findViewById(R.id.backbtn);
        done = findViewById(R.id.donebtn);
        result = findViewById(R.id.result);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
    }
}