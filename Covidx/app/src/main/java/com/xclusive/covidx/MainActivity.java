package com.xclusive.covidx;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fused;
    private List<Data> list;
    private List<StateData> statelist;
    private ArrayList<String> arrayList_states = new ArrayList<>();
    private ArrayList<String> arrayList_active = new ArrayList<>();
    private ArrayList<String> arrayList_confirm = new ArrayList<>();
    private String State1 = "";
    private TextView states_name, confirmed, active_, recovered_, deaths, tconfirmed_, trecovered_, tdearths_, tactives, population1,
            c_confirmed, c_cdelta, c_active, c_adelta, c_recovered, c_rdelta, c_deaths, c_ddelta;
    private PieChart pieChart;
    private BarChart barChart;
    private int confirm, active, recovered, death, todaycases, trecovered, tdeaths, population;
    private CircleImageView countryimg;

    private ProgressBar progressBar;
    private CircularProgressBar circularProgressBar_confirmed, circularProgressBar_active, circularProgressBar_recovered, circularProgressBar_deceased,
            c_cbar, c_abar, c_rbar, c_dbar;

    public static ArrayList<StateData> stateData = new ArrayList<>();
    public static ArrayList<StateData> yourstates = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fused = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            loc();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        pieChart = findViewById(R.id.piechart);
        barChart = findViewById(R.id.barghraph);
        confirmed = findViewById(R.id.textView4);
        active_ = findViewById(R.id.textView7);
        recovered_ = findViewById(R.id.textView10);
        deaths = findViewById(R.id.textView13);
        countryimg = findViewById(R.id.countryimg);
        tconfirmed_ = findViewById(R.id.textView5);
        trecovered_ = findViewById(R.id.textView11);
        tdearths_ = findViewById(R.id.textView14);
        tactives = findViewById(R.id.textView8);

        population1 = findViewById(R.id.population);

        circularProgressBar_active = findViewById(R.id.circularProgressBar1);
        circularProgressBar_recovered = findViewById(R.id.circularProgressBar11);
        circularProgressBar_deceased = findViewById(R.id.circularProgressBar12);
        circularProgressBar_confirmed = findViewById(R.id.circularProgressBar);

        states_name = findViewById(R.id.states_name1);
        c_confirmed = findViewById(R.id.c_total);
        c_active = findViewById(R.id.c_active);
        c_recovered = findViewById(R.id.c_recovered);
        c_deaths = findViewById(R.id.c_deceased);
        c_cdelta = findViewById(R.id.c_delta);
        c_adelta = findViewById(R.id.c_active_delta);
        c_rdelta = findViewById(R.id.c_recovered_delta);
        c_ddelta = findViewById(R.id.c_deceased_delta);

        c_cbar = findViewById(R.id.circularconfirmed);
        c_abar = findViewById(R.id.circularProgressBaractive1);
        c_rbar = findViewById(R.id.circularProgressBarrecovered1);
        c_dbar = findViewById(R.id.circularProgressBardeceased1);


        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        list = new ArrayList<>();
        statelist = new ArrayList<>();
        ///////////textView1 = findViewById(R.id.readdata);////////
        Utilsclass.api().getcountrydata().enqueue(new Callback<List<Data>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                list.addAll(response.body());
                for (int a = 0; a < list.size(); a++) {
                    if (list.get(a).getCountry().equals("India")) {
                        confirm = Integer.parseInt(list.get(a).getCases());
                        active = Integer.parseInt(list.get(a).getActive());
                        recovered = Integer.parseInt(list.get(a).getRecovered());
                        death = Integer.parseInt(list.get(a).getDeaths());
                        todaycases = Integer.parseInt(list.get(a).getTodayCases());
                        trecovered = Integer.parseInt(list.get(a).getTodayRecovered());
                        tdeaths = Integer.parseInt(list.get(a).getTodayDeaths());
                        population = Integer.parseInt(list.get(a).getPopulation());

                        progress();

                        confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(confirm))));
                        active_.setText(NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(active))));
                        recovered_.setText(NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(recovered))));
                        deaths.setText(NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(death))));
                        tconfirmed_.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(todaycases))));
                        tactives.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(todaycases))));
                        trecovered_.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(trecovered))));
                        tdearths_.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(tdeaths))));
                        population1.setText(NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(population))));
                        chart();
                        Log.e("DA", String.valueOf(population));
                        Map<String, String> info = list.get(a).getCountryInfo();

                        Log.e("DA", info.get("flag"));
                        Glide.with(getApplicationContext()).load(info.get("flag")).into(countryimg);
                        //Log.e("DA",list.get(a).getImage());
                        //for number format
                        //String d =  NumberFormat.getInstance().format(Integer.parseInt(list.get(a).getTodaydeaths()));
                        //textView1.append("tdeaths "+d);
                        //Toast.makeText(MainActivity.this, confirm + "/" + active, Toast.LENGTH_SHORT).show();
                        //textView1.append(confirm+"/"+active+"/"+recovered+"/"+death);
                        //System.out.println(confirm+"/"+active);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.e("Err", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    private void cirprogress(CircularProgressBar circularProgressBar, float progress1) {
        circularProgressBar.setProgressMax(100);
        circularProgressBar.setProgressWithAnimation(progress1, (long) 2000); // =1s
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(0f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

        ////


        circularProgressBar.setOnIndeterminateModeChangeListener(isEnable -> {
            // Do something
            return Unit.INSTANCE;
        });

        circularProgressBar.setOnProgressChangeListener(progress -> {
            // Do something
            return Unit.INSTANCE;
        });
    }

    public void chart() {
        //////////configure pie chart///////////////////
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(50);
        pieChart.setEntryLabelTextSize(7);
        // pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Analysis");
        pieChart.setCenterTextSize(10);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pieChart.getLegend().setTextColor(getColor(R.color.grey_m));
        }
        l.setDrawInside(false);
        l.setEnabled(true);

        ////////////////////////////////////////////////

        ArrayList<PieEntry> piedata = new ArrayList<>();

        piedata.add(new PieEntry(confirm, "Confirmed"));
        piedata.add(new PieEntry(active, "Active"));
        piedata.add(new PieEntry(recovered, "Recovered"));
        piedata.add(new PieEntry(death, "Deceased"));

        ArrayList<Integer> colorlist = new ArrayList<>();

        colorlist.add(getResources().getColor(R.color.red_m));
        colorlist.add(getResources().getColor(R.color.blue_m));
        colorlist.add(getResources().getColor(R.color.green_m));
        colorlist.add(getResources().getColor(R.color.grey_m));


        PieDataSet dataSet = new PieDataSet(piedata, "");
        dataSet.setColors(colorlist);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        dataSet.setValueFormatter(new PercentFormatter(pieChart));
        dataSet.setValueTextSize(7f);
        //dataSet.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseOutQuad);
    }

    public void BarChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(1000);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.setGridBackgroundColor(Color.WHITE);
        barChart.setDrawMarkers(true);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int a = 1; a < arrayList_active.size(); a++) {
            barEntries.add(new BarEntry(a, Integer.parseInt(arrayList_active.get(a))));
        }
//        barEntries.add(new BarEntry(2015,420));
//        barEntries.add(new BarEntry(2016,450));
//        barEntries.add(new BarEntry(2017,400));
//        barEntries.add(new BarEntry(2018,320));
//        barEntries.add(new BarEntry(2019,120));


        BarDataSet barDataSet = new BarDataSet(barEntries, "States");
        barDataSet.setColors(MATERIAL_COLORS);

        barDataSet.setValueTextSize(0f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("States Bar");
        barChart.animateY(2000);

    }

    public void progress() {

        ///////////confirmed//////////////
        double a = (double) confirm / population;
        double per1 = a * 100;
        cirprogress(circularProgressBar_confirmed, (float) per1);
        ////active//////
        double a1 = (double) active / confirm;
        double per2 = a1 * 100;
        cirprogress(circularProgressBar_active, (float) per2);
        ////recovered////
        double a2 = (double) recovered / confirm;
        double per3 = a2 * 100;
        cirprogress(circularProgressBar_recovered, (float) per3);
        ////deceased////
        double a3 = (double) death / confirm;
        double per4 = a2 * 100;
        cirprogress(circularProgressBar_deceased, (float) per4);
        ///////////////////////////////////


    }

    public void states(View view) {
        startActivity(new Intent(getApplicationContext(), States.class));

    }

    @SuppressLint("SetTextI18n")
    private void loc() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fused.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();

            if (location != null) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    // localaddress.append("location "+addresses.get(0).getFeatureName()+addresses.get(0).getCountryName());
                    State1 = addresses.get(0).getAdminArea();
                    String locale = getResources().getConfiguration().locale.getDisplayCountry();
                    //localaddress.append("\n local"+locale);
                    loadstates(State1);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fused.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();

            if (location != null) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    // localaddress.setText(addresses.get(0).getFeatureName()+","+addresses.get(0).getLocality()+);
                    State1 = addresses.get(0).getAdminArea();
                    //String locale = getResources().getConfiguration().locale.getDisplayCountry();
                    // localaddress.append("\n local"+locale);

                    loadstates(State1);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadstates(String state1) {
        /////////////////////////////Statewise///////////////////////////

        API_State api_state = Retrofit_stateinstance.getRetrofitstateinstance().create(API_State.class);
        api_state.getStatedata().enqueue(new Callback<State>() {
            @Override
            public void onResponse(Call<State> call, Response<State> response) {
                State state = response.body();
                List<StateData> stateDataList = state.getStateDataList();

                for (int a = 1; a < stateDataList.size(); a++) {
                    //datastate.append("Active"+stateDataList.get(a).getActive()+"| State"+stateDataList.get(a).getState()+"\n");
                    arrayList_states.add(stateDataList.get(a).getState());
                    arrayList_active.add(stateDataList.get(a).getActive());
                    arrayList_confirm.add(stateDataList.get(a).getConfirmed());

                    stateData.add(new StateData(stateDataList.get(a).getActive(), stateDataList.get(a).getConfirmed(),
                            stateDataList.get(a).getDeaths(), stateDataList.get(a).getDeltaconfirmed(), stateDataList.get(a).getDeltadeaths()
                            , stateDataList.get(a).getDeltarecovered(), stateDataList.get(a).getLastupdatetime(), stateDataList.get(a).getRecovered(),
                            stateDataList.get(a).getState(), stateDataList.get(a).getStatecode()));

                    if (stateDataList.get(a).getState().toLowerCase().contains(state1.toLowerCase())) {
                        yourstates.add(new StateData(stateDataList.get(a).getActive(), stateDataList.get(a).getConfirmed(),
                                stateDataList.get(a).getDeaths(), stateDataList.get(a).getDeltaconfirmed(), stateDataList.get(a).getDeltadeaths()
                                , stateDataList.get(a).getDeltarecovered(), stateDataList.get(a).getLastupdatetime(), stateDataList.get(a).getRecovered(),
                                stateDataList.get(a).getState(), stateDataList.get(a).getStatecode()));
                        youstatesfun(yourstates);
                    }
                }


                BarChart();


            }

            @Override
            public void onFailure(Call<State> call, Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void youstatesfun(ArrayList<StateData> yourstates) {
        states_name.setText(yourstates.get(0).getState());
        c_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getConfirmed())));
        c_cdelta.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getDeltaconfirmed())));

        c_active.setText(NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getActive())));
        c_adelta.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getDeltaconfirmed())));

        c_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getRecovered())));
        c_rdelta.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getDeltarecovered())));

        c_deaths.setText(NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getDeaths())));
        c_ddelta.setText("+" + NumberFormat.getInstance().format(Integer.parseInt(yourstates.get(0).getDeltadeaths())));


        ///////////confirmed//////////////
        double a = (double) Integer.parseInt(yourstates.get(0).getConfirmed()) / Integer.parseInt(yourstates.get(0).getConfirmed());
        double per1 = a * 100;
        cirprogress(c_cbar, (float) per1);
        ////active//////
        double a1 = (double) Integer.parseInt(yourstates.get(0).getActive()) / Integer.parseInt(yourstates.get(0).getConfirmed());
        double per2 = a1 * 100;
        cirprogress(c_abar, (float) per2);
        ////recovered////
        double a2 = (double) Integer.parseInt(yourstates.get(0).getRecovered()) / Integer.parseInt(yourstates.get(0).getConfirmed());
        double per3 = a2 * 100;
        cirprogress(c_rbar, (float) per3);
        ////deceased////
        double a3 = (double) Integer.parseInt(yourstates.get(0).getDeaths()) / Integer.parseInt(yourstates.get(0).getConfirmed());
        double per4 = a2 * 100;
        cirprogress(c_dbar, (float) per4);
        ///////////////////////////////////
    }

    public void moveto(View view) {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public"));
        startActivity(viewIntent);
    }

}