package com.xclusive.university_app;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class pdf_reader_activity extends AppCompatActivity {

    private  PDFView pdfView;
    private TextView title1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        pdfView = findViewById(R.id.pdfview);

        String url = getIntent().getStringExtra("PDFFILE");
        String title = getIntent().getStringExtra("PDFTITLE");
        title1=findViewById(R.id.announcepdftitle);
        title1.setText(title);
        new getpdffile().execute(url);


    }

   class getpdffile extends AsyncTask<String,Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }

            }catch (IOException e){
                    return  null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
           pdfView.fromStream(inputStream).load();
        }
    }
}