//Todo: Contribution taken from Kai Morich
//Todo: recent Contribution from Xclisive @jayee
package com.xclusive.newserialmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Dashboard1_fragment extends Fragment implements ServiceConnection,SerialListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ComponentName name;

    ////////////////////////////////////////////
    private enum Connected { False, Pending, True }
    private int deviceId, portNum, baudRate;
    private String newline = "\r\n";
    private TextView receiveText;
    private UsbSerialPort usbSerialPort;
    private SerialService service;
    private boolean initialStart = true;
    private Connected connected = Connected.False;
    private BroadcastReceiver broadcastReceiver;
    private  ControlLines controlLines;
    private   TextView sendText;

    ///////////////////
    //todo: my function items declarations
    public FileOutputStream fileOutputStream;


    private ArrayList  creatininearray;
    private ArrayList usernamearray,genderarray,Agearray,Heightarray,Weightarray,Diabetesarray,Criticalarray;
    private ArrayList Serumarray,Bloodarray,Glucosearray, Bilirubinarray;
    private String   absorbancearray;



    public  boolean wait1=true;//TODO:for send button to work for send and dialog open//
    public static boolean wait2=false;//TODO:for absorbance data to be stored first//
    public boolean Absorbance=false;//TODO:for read absortbance from serial//
    public boolean Absorbance1=true;//TODO:for setdata1() to operate one time only//
    public boolean Creatinine=false;//TODO:for setdata1() to operate one time only//
    public boolean Creatinine1=false;//TODO:for setdata1() to operate one time only//

    public   int c1 = 1, c2= 2, c3=3,c4=4,c5=5,c6=6,c7=7,c8=8,c9=8;


    private Button savebtn,donebtn;

    public LinearLayout donelayout;

    public StringBuilder data= new StringBuilder();
    public StringBuffer creatininebuff = new StringBuffer();
    public StringBuffer absorbancebuff = new StringBuffer();


    //todo dialog layout1 items//
    private EditText username,age,height,weight;
    private RadioGroup gender;
    private RadioButton male,female,other;
    private Button save1;
    public String genderdata="",nametxt,agetxt,heighttxt,weighttxt;

    public Dialog dialog1;
    /////////////////////////////
    //todo dialog layout2 items//
    private EditText bloodpressure,glucose,bilirubin,otherinfo;
    private RadioGroup diabetes,criticaliliness;
    private RadioButton diabetesyes,diabetesno,diabetesidk;
    private RadioButton criticalilinessyes,criticalilinessno,criticalilinesssidk;
    private Button save2;
    public String bloodtxt,serumtxt,glocosetxt,bilirubintxt,otherinfotxt,diabetestxt,criticaltxt;
    private Dialog dialog2;

    /////////////////////////////
    //Todo: creatinine dialog
    private Dialog dialog3;
    private Button creatininedonebtn;
    private TextView creatininedata;
    private Button enterbtn;
    private EditText serum;
    public Dashboard1_fragment() {

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Constants.INTENT_ACTION_GRANT_USB)) {
                    Boolean granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                    connect(granted);
                }
            }
        };
    }
    //TODO: Rename and change types and number of parameters
    public static Dashboard1_fragment newInstance(String param1, String param2) {
        Dashboard1_fragment fragment = new Dashboard1_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
        setRetainInstance(true);

        //TODO: taken from previous fragment i.e devicefragment
        deviceId = getArguments().getInt("device");
        portNum = getArguments().getInt("port");
        baudRate = getArguments().getInt("baud");
        //todo///////////////////////////////////////////////////
    }
    @Override
    public void onDestroy() {

         if(connected!=Connected.False){
            disconnect();
            getActivity().stopService(new Intent(getActivity(), SerialService.class));
            super.onDestroy();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if(service != null)
            service.attach(this);
        else
            getActivity().startService(new Intent(getActivity(), SerialService.class)); // prevents service destroy on unbind from recreated activity caused by orientation change
    }
    @Override
    public void onStop() {
        if(service != null && !getActivity().isChangingConfigurations())
            service.detach();
        super.onStop();
    }
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        getActivity().bindService(new Intent(getActivity(), SerialService.class), this, Context.BIND_AUTO_CREATE);
        //todo: while the device is attach bindservice is called
    }
    @Override
    public void onDetach() {
        //todo: while the device is detached unibindservice is called
        try { getActivity().unbindService(this); } catch(Exception ignored) {}
        super.onDetach();
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Constants.INTENT_ACTION_GRANT_USB));
        if(initialStart && service != null) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
        if(controlLines != null && connected == Connected.True)
            controlLines.start();
    }
    @Override
    public void onPause() {
        getActivity().unregisterReceiver(broadcastReceiver);
        if(controlLines != null)
            controlLines.stop();
        super.onPause();
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((SerialService.SerialBinder) binder).getService();
        service.attach(this);
        if(initialStart && isResumed()) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    //todo:***************************************************** UI *********************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dashboard1_fragment, container, false);

        receiveText = view.findViewById(R.id.receive_text);                          // TextView performance decreases with number of spans
        receiveText.setTextColor(getResources().getColor(R.color.black)); // set as default color to reduce number of spans
        receiveText.setMovementMethod(ScrollingMovementMethod.getInstance());


        creatininearray = new ArrayList<String>();
        usernamearray = new ArrayList<String>(); genderarray=new ArrayList<String>();Agearray =new ArrayList<String>();
        Heightarray =new ArrayList<String>(); Weightarray =new ArrayList<String>(); Diabetesarray =new ArrayList<String>();
        Criticalarray =new ArrayList<String>(); Serumarray =new ArrayList<String>(); Bloodarray = new ArrayList<String>();
        Bilirubinarray = new ArrayList<String>();
        Glucosearray = new ArrayList<String >();


         sendText = view.findViewById(R.id.send_text);
         savebtn = view.findViewById(R.id.savebtn);
         donebtn = view.findViewById(R.id.done_btn);
         donelayout = view.findViewById(R.id.donelinearlayout);
         Button clearbtn = view.findViewById(R.id.clearbtn);


         initilazition();

        creatininedonebtn.setEnabled(false);
        enterbtn.setEnabled(true);
 //todo dialog fields///////////////////////////////////
        donebtn.setEnabled(false);


       donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wait1){

                     send(serum.getText().toString());
                     wait1 = false;//todo: to activate insertion in array

                    }
              else {
                      Absorbance=false;
                      Absorvance();
                      Creatinine=true;//todo: creatinine array to work
                      dialog1.show();
                  }

            }
        });
        savebtn.setOnClickListener(v -> {

            if(receiveText.getText().toString().isEmpty()){
                return;
            }else {


                 createtxtfile(v);
          }
        });
        clearbtn.setOnClickListener(v -> {
            clear_receivetext();
        });

        //todo: dialogs btns
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nametxt = username.getText().toString().trim();
                agetxt = age.getText().toString().trim();
                heighttxt = height.getText().toString().trim();
                weighttxt = weight.getText().toString().trim();
                int radiogender = gender.getCheckedRadioButtonId();
                int radiomale = male.getId();
                int radiofemale = female.getId();
                int radioother = other.getId();

                if(radiogender == radiomale){
                    genderdata = "M";


                }
                else if(radiogender == radiofemale){
                    genderdata = "F";

                }
                else   if(radiogender == radioother){
                    genderdata = "O";

                }
                usernamearray.add(nametxt);
                genderarray.add(genderdata);
                Agearray.add(agetxt);
                Heightarray.add(heighttxt);
                Weightarray.add(weighttxt);

                dialog1.dismiss();
                dialog2.show();

            }
        });
  save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validdiabetes() | !validcritical()){
                    return;
                }
                else {
                    bloodtxt = bloodpressure.getText().toString().trim();
                    //serumtxt = serum.getText().toString().trim();
                    glocosetxt = glucose.getText().toString().trim() ;
                    bilirubintxt = bilirubin.getText().toString().trim();
                    otherinfotxt = otherinfo.getText().toString().trim();
                    int diabetes1 = diabetes.getCheckedRadioButtonId();
                    int yesdia = diabetesyes.getId();
                    int nodia = diabetesno.getId();
                    int idkdia = diabetesidk.getId();
                    if(diabetes1 == yesdia){
                       diabetestxt = "Yes";


                    }
                    else if(diabetes1 ==nodia){
                        diabetestxt = "No";

                    }
                    else   if(diabetes1 == idkdia){
                        diabetestxt = "IDK";

                    }
                    int critilcal1 = criticaliliness.getCheckedRadioButtonId();
                    int criyes = criticalilinessyes.getId();
                    int crino = criticalilinessno.getId();
                    int criidk  = criticalilinesssidk.getId();
                    if(critilcal1 == criyes){
                        criticaltxt = "Yes";


                    }
                    else if(critilcal1 ==crino){
                        criticaltxt  = "No";

                    }
                    else   if(critilcal1 == criidk){
                        criticaltxt  = "IDK";

                    }


//                        Diabetesarray.add(diabetestxt);
//                        Criticalarray.add(criticaltxt);
//                        //Serumarray.add(serumtxt);
//                        Bloodarray.add(bloodtxt);
//                        Glucosearray.add(glocosetxt);
//                        Bilirubinarray.add(bilirubintxt);
                        //Toast.makeText(getContext(), "creatininebuff.toString()", Toast.LENGTH_SHORT).show();
                        dialog2.dismiss();
                        dialog3.show();





                }
            }
        });
        ///todo: creatinine dialog
        enterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serum.getText().toString().isEmpty()){
                    serum.setError("Empty!!");
                    return;
                }else
                {   serumtxt = serum.getText().toString().trim();
                    send(serum.getText().toString());
                    serum.setText("");
                    creatininedonebtn.setEnabled(true);
                    enterbtn.setEnabled(false);

                }

            }
        });
        creatininedonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Creatinine();
                creatininedata.setText("");
                dialog3.dismiss();
                enterbtn.setEnabled(true);
            }
        });


        ////////////////////




        controlLines = new ControlLines(view);
        return view;
    }

    private void  Absorvance() {

        if(Absorbance1){
            data.append("Primary HealthTect Pvt Ltd\n");
            data.append("Absorbance of The Standard : "+absorbancebuff);
            data.append("\n");
            data.append("========================================\n");
            Absorbance1 = false;

           }

    }
    private void  Creatinine() {

            //todo: for getting time date
            //data.append("Sample Name: " + serum.getText().toString() + "\n");
            Date currentdate = new Date();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String c = df.format(currentdate);
            SimpleDateFormat clocktime = new SimpleDateFormat("h:mm a");
            SimpleDateFormat day = new SimpleDateFormat("EEEE");
            String time = clocktime.format(currentdate);
            String day1 = day.format(currentdate);

            data.append("========================================\n");
            data.append("Date: " + day1 + "," + c + "\t| Time: " + time + ",\n");
            //////////////////////////////////////////////////////////////////////
//            data.append("\"Absorbance\""+","+"\"name\""+","+"\"gender\""+","+"\"age\""+","+"\"height\""+"," +
//                        ""+"\"weight\""+","+"\"Dia\""+","+"\"critical\""+","+"\"Serum\""+","+"\"blood\""+","+
//                         "\"glucose\""+","+"\"bilirubin\""+","+"\"Creatinine\""+"\n");
//            for(int i=0;i<usernamearray.size();i++){
//                   data.append(Serumarray.get(i).toString()+","+usernamearray.get(i).toString()+","+genderarray.get(i).toString()+","+Agearray.get(i).toString()+","+Heightarray.get(i).toString()
//                           +","+Weightarray.get(i).toString()+","+Diabetesarray.get(i).toString()+","+Criticalarray.get(i).toString()+","+
//                          "Absorbance" +","+Bloodarray.get(i).toString()+","+Bilirubinarray.get(i).toString()+","+creatininearray.get(i).toString()+"\n");
//            }

            data.append("name: " + nametxt+ "\t");
            data.append("Gender: " + genderdata + "\t");
            data.append("Age: " + agetxt + "\n");
            data.append("Height: " + heighttxt + "\t");
            data.append("Weight: " + weighttxt + "Kg" + "\n");
            data.append("Diabetes :" + diabetestxt + "\t| Critical Illness :" + criticaltxt + "\n");
            data.append("Blood Pressuser: " + bloodtxt + "\n");
            data.append("Serum Creatinine: " + serumtxt + "\n");
            data.append("Glucose: " + glocosetxt + "\t");
            data.append("| Brilirubin: " + bilirubintxt + "\n");
            data.append("Other Information: " + otherinfotxt + "\n");
            data.append("///////////////////////"+ "\n");
            data.append("Test Sample Data:\n");


            data.append("Creatinine : "+creatininebuff.toString());
            data.append("\n");
            data.append("========================================\n");

            //Todo: clearing the previous data from Edittext, variables, arraylist etc.username.setText("");
            username.setText("");
            age.setText("");
            height.setText("");
            weight.setText("");
            bloodpressure.setText("");
            serum.setText("");
            glucose.setText("");
            bilirubin.setText("");
            otherinfo.setText("");

            creatininearray.clear();


            creatininebuff.delete(0,creatininebuff.length());

    }

    private boolean validdiabetes() {
        int radiodcritical = criticaliliness.getCheckedRadioButtonId();
        int yes = criticalilinessyes.getId();
        int no =criticalilinessno.getId();
        int idk = criticalilinesssidk.getId();

        if(radiodcritical==yes){
            criticaltxt= "Yes";
            return true;

        }
        else if(radiodcritical == no){
            criticaltxt= "No";
            return true;
        }
        else   if(radiodcritical == idk){
            criticaltxt = "Idk";
            return true;
        }
        else {
            return false;
        }
    }
    private boolean validcritical() {
        int radiodiabetes = diabetes.getCheckedRadioButtonId();
        int yes = diabetesyes.getId();
        int no =diabetesno.getId();
        int idk = diabetesidk.getId();

        if(radiodiabetes ==yes){
            diabetestxt= "Yes";
            return true;

        }
        else if(radiodiabetes == no){
            diabetestxt= "No";
            return true;
        }
        else   if(radiodiabetes == idk){
            diabetestxt = "Idk";
            return true;
        }
        else {
            return false;
        }
    }
    private void initilazition() {
        //todo dialog layout1 items//
        dialog1 = new Dialog(getContext());
        dialog1.setContentView(R.layout.dialog1);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        username =  dialog1.findViewById(R.id.username1);
        age =  dialog1.findViewById(R.id.age);
        height =  dialog1.findViewById(R.id.height);
        weight =  dialog1.findViewById(R.id.weight);

        gender =  dialog1.findViewById(R.id.gender);
        male =  dialog1.findViewById(R.id.male1);
        female =  dialog1.findViewById(R.id.female1);
        other =  dialog1.findViewById(R.id.other1);
        save1 =  dialog1.findViewById(R.id.savebtn1);

        /////////////////////////////
        // todo dialog layout2 item initialization
        dialog2 = new Dialog(getContext());
        dialog2.setContentView(R.layout.dialog2);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        bloodpressure = dialog2.findViewById(R.id.bloodpressuretxt);

        bilirubin = dialog2.findViewById(R.id.bilirubintxt);
        otherinfo = dialog2.findViewById(R.id.otherinfo);
        glucose = dialog2.findViewById(R.id.glucosetxt);

        diabetes = dialog2.findViewById(R.id.diabetes);
        diabetesyes = dialog2.findViewById(R.id.diabetesyes);
        diabetesno = dialog2.findViewById(R.id.diabetesno);
        diabetesidk = dialog2.findViewById(R.id.diabetesIdk);
        criticaliliness = dialog2.findViewById(R.id.critical);
        criticalilinessyes = dialog2.findViewById(R.id.criticalyes);
        criticalilinessno = dialog2.findViewById(R.id.criticalno);
        criticalilinesssidk = dialog2.findViewById(R.id.criticalidk);
        save2 = dialog2.findViewById(R.id.savebtn2);
////////////////////////////////////
        //Todo: creatinine dialog
        dialog3= new Dialog(getContext());
        dialog3.setContentView(R.layout.creatininelayout);
        dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        serum= dialog3.findViewById(R.id.serumtxt);
        creatininedonebtn = dialog3.findViewById(R.id.creatininedonebtn);
        creatininedata = dialog3.findViewById(R.id.creatininedata);
        enterbtn = dialog3.findViewById(R.id.enterbtn);
    }
    private void clear_receivetext() {
        receiveText.setText("");
        creatininearray.clear();
        absorbancearray=" ";
//        creatinine.clear();
//        Abrorbanse.clear();
//        dataarraylist.clear();
//        dataarraylist2.clear();
    }
    private void createtxtfile(View v) {

      try {

            fileOutputStream = getContext().openFileOutput("Primary_HealthTech.txt", Context.MODE_PRIVATE);
            fileOutputStream.write((data.toString()).getBytes());
            fileOutputStream.close();

            //todo:
            Context context =getContext().getApplicationContext();
            File file = new File(getContext().getFilesDir(),"Primary_HealthTech.txt");
            Uri path = FileProvider.getUriForFile(context,"com.xclusive.newserialmonitor.fileprovider",file);
            Intent intentcsv = new Intent(Intent.ACTION_SEND);
            intentcsv.setType("text/txt");
            intentcsv.putExtra(Intent.EXTRA_SUBJECT,file);
            intentcsv.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentcsv.putExtra(Intent.EXTRA_STREAM,path);
            startActivity(Intent.createChooser(intentcsv,"Share With Drive"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //todo////////////////////////////////////////////////////////////////////////////////////

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_terminal, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.clear) {
//            receiveText.setText("");
//            return true;
//        } else if (id ==R.id.newline) {
//            String[] newlineNames = getResources().getStringArray(R.array.newline_names);
//            String[] newlineValues = getResources().getStringArray(R.array.newline_values);
//            int pos = java.util.Arrays.asList(newlineValues).indexOf(newline);
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle("Newline");
//            builder.setSingleChoiceItems(newlineNames, pos, (dialog, item1) -> {
//                newline = newlineValues[item1];
//                dialog.dismiss();
//            });
//            builder.create().show();
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }

//todo: UI +Serial

    private void connect() {
        connect(null);
    }
    private void connect(Boolean permissionGranted) {
        UsbDevice device = null;
        UsbManager usbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        for(UsbDevice v : usbManager.getDeviceList().values())
            if(v.getDeviceId() == deviceId)
                device = v;
        if(device == null) {
            status("Device Not Found...");
            return;
        }
        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        if(driver == null) {
            driver = CustomProber.getCustomProber().probeDevice(device);
        }
        if(driver == null) {
            status("connection failed: no driver for device");
            return;
        }
        if(driver.getPorts().size() < portNum) {
            status("connection failed: not enough ports at device");
            return;
        }
        usbSerialPort = driver.getPorts().get(portNum);
        UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
        if(usbConnection == null && permissionGranted == null && !usbManager.hasPermission(driver.getDevice())) {
            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(Constants.INTENT_ACTION_GRANT_USB), 0);
            usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
            return;
        }
        if(usbConnection == null) {
            if (!usbManager.hasPermission(driver.getDevice()))
                status("connection failed: permission denied");
            else
                status("connection failed: open failed");
            return;
        }

        connected = Connected.Pending;
        try {
            usbSerialPort.open(usbConnection);
            usbSerialPort.setParameters(baudRate, UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            SerialSocket socket = new SerialSocket(getActivity().getApplicationContext(), usbConnection, usbSerialPort);
            service.connect(socket);
            // usb connect is not asynchronous. connect-success and connect-error are returned immediately from socket.connect
            // for consistency to bluetooth/bluetooth-LE app use same SerialListener and SerialService classes
            onSerialConnect();
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }
    private void disconnect() {
        connected = Connected.False;
        controlLines.stop();
        service.disconnect();
        usbSerialPort = null;
    }
    private void send(String str) {
        if(connected != Connected.True) {
            Toast.makeText(getActivity(), "Device Not Connected...", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SpannableStringBuilder spn = new SpannableStringBuilder(str+'\n');
            spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorSendText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //receiveText.append(spn);
            byte[] data = (str + newline).getBytes();//todo :  convert String to Hex(bytes) and send in serial
            service.write(data);
            ///

         } catch (Exception e) {
            onSerialIoError(e);
        }
    }
    private void receive(byte[] data) {
        //todo : function to convert Hex to String and append to text view
        //todo: public static String c1 = "A", c2= "B", c3="C",c4="D",c5="E",c6="F",c7="G",c8="H",c9="I";
        //receiveText.append(new String(data));

        donebtn.setEnabled(true);
        String  a2=new String(data);


        if(a2.contains("A")){
            receiveText.append("Calibrating Device , Please Wait....\n");
            a2 ="";
            return;

        }
        if(a2.contains("B")){
            receiveText.append("Insert Standard And Press Done\n");
            a2 ="";
            return;

        }
        if(a2.contains("G")){
            receiveText.append("Absorbance of the Standard :");
            Absorbance = true;
            return;
        }
        if(Absorbance){
            String  a1=new String(data);
                if(a1.contains("H")){
                    receiveText.append("Insert Sample and Press Done :\n");
                }
                else {
                    receiveText.append(new String(data));
                    absorbancebuff.append(new String(data));
                }
            return;
        }

        if(a2.contains("H")){
            receiveText.append("Press Done to Re-test now\n");
            a2="";
            return;
        }
        if(Creatinine)
         {

          creatininedata.append(new String(data));
          creatininebuff.append(new String(data));

         }


    }

    void status(String str) {
        Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
//        SpannableStringBuilder spn = new SpannableStringBuilder(str+'\n');
//        spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        receiveText.append(spn);
    }

    /*
     * SerialListener
     */
    @Override
    public void onSerialConnect() {
        status("connected");
//        receiveText.setText("Calibrating Device, Please Wait.....\n");
//        receiveText.setText("Insert Standard and Press Done :\n");
        connected = Connected.True;
        controlLines.start();
    }

    @Override
    public void onSerialConnectError(Exception e) {
        status("connection failed: " + e.getMessage());
        disconnect();
    }

    @Override
    public void onSerialRead(byte[] data) { //todo: receving Data from serial port i.e Ardunio sends data from serial
        receive(data);
    }

    @Override
    public void onSerialIoError(Exception e) {
        status("connection lost: " + e.getMessage());
        disconnect();
    }

    class ControlLines {
        private static final int refreshInterval = 200; // msec

        private Handler mainLooper;
        private Runnable runnable;
        private ToggleButton rtsBtn, ctsBtn, dtrBtn, dsrBtn, cdBtn, riBtn;

        ControlLines(View view) {
            mainLooper = new Handler(Looper.getMainLooper());
            runnable = this::run; // w/o explicit Runnable, a new lambda would be created on each postDelayed, which would not be found again by removeCallbacks

            rtsBtn = view.findViewById(R.id.controlLineRts);
            ctsBtn = view.findViewById(R.id.controlLineCts);
            dtrBtn = view.findViewById(R.id.controlLineDtr);
            dsrBtn = view.findViewById(R.id.controlLineDsr);
            cdBtn = view.findViewById(R.id.controlLineCd);
            riBtn = view.findViewById(R.id.controlLineRi);
            rtsBtn.setOnClickListener(this::toggle);
            dtrBtn.setOnClickListener(this::toggle);
        }

        private void toggle(View v) {
            ToggleButton btn = (ToggleButton) v;
            if (connected != Connected.True) {
                btn.setChecked(!btn.isChecked());
                Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
                return;
            }
            String ctrl = "";
            try {
                if (btn.equals(rtsBtn)) { ctrl = "RTS"; usbSerialPort.setRTS(btn.isChecked()); }
                if (btn.equals(dtrBtn)) { ctrl = "DTR"; usbSerialPort.setDTR(btn.isChecked()); }
            } catch (IOException e) {
                status("set" + ctrl + " failed: " + e.getMessage());
            }
        }

        private void run() {
            if (connected != Connected.True)
                return;
            try {
                EnumSet<UsbSerialPort.ControlLine> controlLines = usbSerialPort.getControlLines();
                rtsBtn.setChecked(controlLines.contains(UsbSerialPort.ControlLine.RTS));
                ctsBtn.setChecked(controlLines.contains(UsbSerialPort.ControlLine.CTS));
                dtrBtn.setChecked(controlLines.contains(UsbSerialPort.ControlLine.DTR));
                dsrBtn.setChecked(controlLines.contains(UsbSerialPort.ControlLine.DSR));
                cdBtn.setChecked(controlLines.contains(UsbSerialPort.ControlLine.CD));
                riBtn.setChecked(controlLines.contains(UsbSerialPort.ControlLine.RI));
                mainLooper.postDelayed(runnable, refreshInterval);
            } catch (IOException e) {
                status("getControlLines() failed: " + e.getMessage() + " -> stopped control line refresh");
            }
        }

        void start() {
            if (connected != Connected.True)
                return;
            try {
                EnumSet<UsbSerialPort.ControlLine> controlLines = usbSerialPort.getSupportedControlLines();
                if (!controlLines.contains(UsbSerialPort.ControlLine.RTS)) rtsBtn.setVisibility(View.INVISIBLE);
                if (!controlLines.contains(UsbSerialPort.ControlLine.CTS)) ctsBtn.setVisibility(View.INVISIBLE);
                if (!controlLines.contains(UsbSerialPort.ControlLine.DTR)) dtrBtn.setVisibility(View.INVISIBLE);
                if (!controlLines.contains(UsbSerialPort.ControlLine.DSR)) dsrBtn.setVisibility(View.INVISIBLE);
                if (!controlLines.contains(UsbSerialPort.ControlLine.CD))   cdBtn.setVisibility(View.INVISIBLE);
                if (!controlLines.contains(UsbSerialPort.ControlLine.RI))   riBtn.setVisibility(View.INVISIBLE);
                run();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "getSupportedControlLines() failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        void stop() {
            mainLooper.removeCallbacks(runnable);
            rtsBtn.setChecked(false);
            ctsBtn.setChecked(false);
            dtrBtn.setChecked(false);
            dsrBtn.setChecked(false);
            cdBtn.setChecked(false);
            riBtn.setChecked(false);
        }
    }


}