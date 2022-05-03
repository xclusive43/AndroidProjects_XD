package com.xclusive.x_note;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.xclusive.x_note.Adapter.ColorAdapter;
import com.xclusive.x_note.SQlite.DataBase;
import com.xclusive.x_note.model.ColorModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import top.defaults.colorpicker.ColorPickerPopup;
import top.defaults.colorpicker.ColorPickerView;

import static com.xclusive.x_note.savednotes.Adapter_notes;

public class MainActivity extends AppCompatActivity {
   ImageView colorpicker;
   MaterialButton donebtn;
   Bitmap bitmap;
    String hexcolor = "#FFFFFFFF";
   public static EditText input,Title,subtitle;
   public static Dialog colordialog;
   public static String title1,subtitle1,data1;
    RecyclerView colorview;
    ColorAdapter colorAdapter;
    ArrayList<ColorModel> colorModels = new ArrayList<>();
    private FloatingActionButton voicebtn;

    public static ConstraintLayout editlayout,relativeLayout;
    public static String color ="#FFFFFFFF",id="";

    public static Cursor cursor;
    public static DataBase db;


    public static int update;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},101);
            }
        }

        input = findViewById(R.id.input);
        relativeLayout = findViewById(R.id.relativeLayout);
        Title = findViewById(R.id.Title1);
        subtitle = findViewById(R.id.subtitle);
        voicebtn = findViewById(R.id.voicebtn);
        db = new DataBase(this);
        editlayout = findViewById(R.id.editlayout);

if (update==1){
    Title.setText(title1);
    subtitle.setText(subtitle1);
    input.setText(data1);
    editlayout.setBackgroundColor(Color.parseColor(color));
    relativeLayout.setBackgroundColor(Color.parseColor(color));
  // getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, Integer.parseInt(String.valueOf(Color.parseColor(color)))));
}
else {
    Title.setText("");
    subtitle.setText("");
    input.setText("");
    editlayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
    relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
}



        //todo color pallet dialog
        colordialog = new Dialog(this);
        colordialog.setContentView(R.layout.colorpallet_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(colordialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        colordialog.getWindow().setAttributes(lp);

        colordialog.setCanceledOnTouchOutside(true);

        colorview = colordialog.findViewById(R.id.recyclerviewcolor);
        colorpicker = colordialog.findViewById(R.id.colorpicker1);
        donebtn = colordialog.findViewById(R.id.colordonebtn);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 4);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        colorview.setLayoutManager(linearLayoutManager);

        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFF4E0"));
        colorModels.add(new ColorModel("#FFE9EDFF"));
        colorModels.add(new ColorModel("#FFFAEEE7"));
        colorModels.add(new ColorModel("#FFE7FAE9"));
        colorModels.add(new ColorModel("#FFFFFFFF"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));
        colorModels.add(new ColorModel("#FFFFE9F5"));


        colorAdapter = new ColorAdapter(colorModels);
        colorview.setAdapter(colorAdapter);
        colorAdapter.notifyDataSetChanged();



//todo:mic btn function for translating speech to text using google translator//
        voicebtn.setOnClickListener(v->{

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            try {
                startActivityForResult(intent, 1);

            }catch (ActivityNotFoundException e){
                Toast.makeText(getApplicationContext(),"Your Device doesn't Support this Feature",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        });

colorpicker.setDrawingCacheEnabled(true);
colorpicker.buildDrawingCache(true);

colorpicker.setOnTouchListener((v, event) -> {

    if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
        bitmap = colorpicker.getDrawingCache();
        int pix = bitmap.getPixel((int)event.getX(),(int)event.getY());

        int R = Color.red(pix);
        int G = Color.red(pix);
        int B = Color.blue(pix);
        hexcolor = "#"+Integer.toHexString(pix);

        donebtn.setBackgroundColor(Color.parseColor(hexcolor));

    }
        return true;
});


donebtn.setOnClickListener(V->{
    if (!hexcolor.isEmpty()){
        editlayout.setBackgroundColor(Color.parseColor(hexcolor));
        relativeLayout.setBackgroundColor(Color.parseColor(hexcolor));
        colordialog.dismiss();
    }

});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode== RESULT_OK && data !=null){
                    ArrayList<String> info = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input.append(" "+info.get(0));
                }
            case 101:
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                FirebaseVision firebaseVision = FirebaseVision.getInstance();
                FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
                Task<FirebaseVisionText> textTask = firebaseVisionTextRecognizer.processImage(image);
                 textTask.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                     @Override
                     public void onSuccess(FirebaseVisionText firebaseVisionText) {
                         if (textTask.isSuccessful()){

                             input.append(firebaseVisionText.getText().toString());
                             //Toast.makeText(getApplicationContext(), firebaseVisionText.getText().toString(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
                 textTask.addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull @NotNull Exception e) {

                     }
                 });


                break;
        }
    }


    public void save(View view) {
    if (color.isEmpty()){
        color = "#FFFFFFFF";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            savedata();
        }
    }
    else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            savedata();
        }
    }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void savedata() {
        String month ="";

        String MM = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        String DD = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        String YY = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            YY = new SimpleDateFormat("YYYY").format(Calendar.getInstance().getTime());
        }
        switch (MM){
            case "01":  month =DD+" January, "+YY;
                break;
            case "02":  month =DD+" February, "+YY;
                break;
            case "03":  month =DD+" March, "+YY;
                break;
            case "04":  month =DD+" April, "+YY;
                break;
            case "05":  month =DD+" May, "+YY;
                break;
            case "06":  month =DD+" June, "+YY;
                break;
            case "07":  month =DD+" July, "+YY;
                break;
            case "08":  month =DD+" August, "+YY;
                break;
            case "09":  month =DD+" September, "+YY;
                break;
            case "10":  month =DD+" October, "+YY;
                break;
            case "11":  month =DD+" November, "+YY;
                break;
            case "12":  month =DD+" December, "+YY;
                break;
        }

        if (Title.getText().toString().isEmpty() | subtitle.getText().toString().isEmpty() | input.getText().toString().isEmpty()){

            Toast.makeText(this, "Title and Subtitle are mandatory", Toast.LENGTH_SHORT).show();

        }
        else {
            if (update==0){

                db = new DataBase(this);
                int res = db.insertdata("0",Title.getText().toString(), subtitle.getText().toString(), "null",month,input.getText().toString(), hexcolor,1);
                //Adapter_notes.notifyDataSetChanged();

                Intent intent = new Intent(getApplicationContext(),savednotes.class);
                startActivity(intent);
                finish();
            }
            else{
                update =0;
             db = new DataBase(this);
             db.update(id,Title.getText().toString(), subtitle.getText().toString(), "null",month,input.getText().toString(), hexcolor);

                Intent intent = new Intent(getApplicationContext(),savednotes.class);
                startActivity(intent);
                finish();

            }

        }

    }
    public void bacbtn(View view) {
        startActivity(new Intent(this,savednotes.class));
        finish();



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,savednotes.class));
        finish();
        super.onBackPressed();
    }

    public void bold(View view) {
        Spannable bold = new SpannableStringBuilder(input.getText());
        bold.setSpan(new StyleSpan(Typeface.BOLD),
                input.getSelectionStart(),
                input.getSelectionEnd(),
                0);
        input.setText(bold);
    }

    public void redo(View view) {
    }

    public void undo(View view) {
    }

    public void color(View view) {
//        Spannable bold = new SpannableStringBuilder(input.getText());
//        bold.setSpan(new Color(Color.parseColor("#sdfsf")),
//                input.getSelectionStart(),
//                input.getSelectionEnd(),
//                0);
//        input.setText(bold);
    }



    public void underline(View view) {
        Spannable bold = new SpannableStringBuilder(input.getText());
        bold.setSpan(new UnderlineSpan(),
                input.getSelectionStart(),
                input.getSelectionEnd(),
                0);
        input.setText(bold);
    }

    public void italic(View view) {
        Spannable bold = new SpannableStringBuilder(input.getText());
        bold.setSpan(new StyleSpan(Typeface.ITALIC),
                input.getSelectionStart(),
                input.getSelectionEnd(),
                0);
        input.setText(bold);
    }

    public void Normal(View view) {
        Spannable n = new SpannableStringBuilder(input.getText());
        n.setSpan(new StyleSpan(0),
                input.getSelectionStart(),
                input.getSelectionEnd(),
                0);
        input.setText(n);
    }

    public void selectcolor(View view) {

        String mDefaultColor = "#FFFFFFFF";
        colordialog.show();


    }

    public void opencamera(View view) {
        Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,101);
    }


}