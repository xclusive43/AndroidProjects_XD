package com.xclusive.abcmart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    private static  final int home_fragment =0;
    private static final int cart_fragment=1;
    private static final int order_fragment=2;
    private static final int mywishlist_fragment=3;
    private static final int my_coupons_fragment=4;
    private static final int my_account_fragment=5;

    public static Boolean showcart = false;//for cart button in product details activity
    private  int current_fragment=-1;

    ActionBarDrawerToggle toggle;
    private FrameLayout frameLayout;

    NavigationView navigationView;
    private ImageView actionbarlogo;
    private  Dialog signindialog;
    public static  DrawerLayout drawer;
   public static FirebaseUser currentuser;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        Toolbar toolbar = findViewById(R.id.toolbar);
        actionbarlogo = findViewById(R.id.actionbarlogo);
        setSupportActionBar(toolbar);


          drawer = findViewById(R.id.drawer_layout);//NAV DRAWER LAYOUT

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        //frame//
        frameLayout = findViewById(R.id.main_frame_layout);

        int i=1;
        if(showcart){
            drawer.setDrawerLockMode(i);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            allfragmentmethod("My Cart",new my_cart_fragment(),-2);

        }else {

            toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.draweropen,R.string.drawerclose);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new homeFragment(),home_fragment);
        }



        signindialog = new Dialog(MainActivity2.this);
        signindialog.setContentView(R.layout.dialog_sign_and_signup);
        signindialog.setCancelable(true);//TODO IF USER CLICK OUT THE DIALOG THE DIALOG WILL BE CLOSED
        signindialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signin,signup;
        signin =signindialog.findViewById(R.id.signinbtn);
        signup =signindialog.findViewById(R.id.signupbtn);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signindialog.dismiss();//TODO: TO CLOSE THE DIALOG ON THE IT MOVE TO NEXT ACTIVITY
                Intent signinitent = new Intent(MainActivity2.this, login.class);
                startActivity(signinitent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signindialog.dismiss();//TODO: TO CLOSE THE DIALOG ON THE IT MOVE TO NEXT ACTIVITY
                Intent signinitent1 = new Intent(MainActivity2.this, Signup.class);
                startActivity(signinitent1);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentuser == null ){

            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }
        else {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if(current_fragment==home_fragment){

                current_fragment=-1;//for reloading the home fragment ...
                super.onBackPressed();
            }
            else {
                if(showcart){
                    showcart =false;
                    finish();


                }else {
                    actionbarlogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu(); //for showing action bar icons like cart, search .etc
                    setFragment(new homeFragment(), home_fragment);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(current_fragment==home_fragment) {
            getSupportActionBar().setDisplayShowTitleEnabled(false );//for invisible the title in action bar
            getMenuInflater().inflate(R.menu.main_activity2, menu);
        }
//        if(current_fragment==cart_fragment) {
//
//            getMenuInflater().inflate(R.menu.main_activity2, menu);
//        }

        return true;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.search) //for search top nav icon
        {
            return true;
        }
        else if (id==R.id.cart) //for cart icon
        {   if(currentuser ==null){
            signindialog.show();
        }else
        {
            allfragmentmethod("My Cart",new my_cart_fragment(),cart_fragment);
        }




            return true;
        }
        else if(id==R.id.notification) //for notification icon
        {
            return true;
        }

        else if(id==android.R.id.home){
            if(showcart){
                showcart=false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


//    private void mycart() {
//        actionbarlogo.setVisibility(View.GONE);
//        mycartlogo.setVisibility(View.VISIBLE);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        invalidateOptionsMenu();
//
//        getSupportActionBar().setTitle("My Cart");
//
//
//        setFragment(new my_cart_fragment(),cart_fragment);
//        navigationView.getMenu().getItem(3).setChecked(true);//passing index 3 for cart logo in nav bar to be marked as checked
//    }

    private void allfragmentmethod(String fragment_title, Fragment fragment,int fragmnet_no) {
        actionbarlogo.setVisibility(View.GONE);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        invalidateOptionsMenu();

        getSupportActionBar().setTitle(fragment_title);

        setFragment(fragment,fragmnet_no);
        if (fragmnet_no == cart_fragment){
        navigationView.getMenu().getItem(2).setChecked(true);//passing index 2 for cart logo in nav bar to be marked as checked
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(currentuser!=null) {//todo if the user is null then if he click at any nav btn then he will redirect to signin-dialog()


            int id = item.getItemId();
            if (id == R.id.nav_home) {
                actionbarlogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu(); //for showing action bar icons like cart, search .etc
                setFragment(new homeFragment(), home_fragment);
            }
            if (id == R.id.nav_myorders) {
                allfragmentmethod("My Orders", new my_orders_fragment(), order_fragment);
            } else if (id == R.id.nav_cart) {
                allfragmentmethod("My Cart", new my_cart_fragment(), cart_fragment);
            } else if (id == R.id.nav_wishlist) {
                allfragmentmethod("My Wishlist", new mywishlist_fragment(), mywishlist_fragment);
            } else if (id == R.id.nav_coupons) {
                allfragmentmethod("My Coupons", new my_rewards_Fragment(), my_coupons_fragment);
            } else if (id == R.id.nav_account) {
                allfragmentmethod("My Account", new my_account_Fragment(), my_account_fragment);
            } else if (id == R.id.nav_notification) {

            } else if (id == R.id.nav_contact) {

            } else if (id == R.id.nav_help) {
                //Toast.makeText(MainActivity2.this,"hello",Toast.LENGTH_LONG).show();
            }
            else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent signin = new Intent(MainActivity2.this, login.class);
                startActivity(signin);
                finish();
                //Toast.makeText(MainActivity2.this,"hello",Toast.LENGTH_LONG).show();
            }


            drawerLayout.closeDrawer(GravityCompat.START);
            return  true;

        }else
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            signindialog.show();
            return false;
        }

    }


    private void setFragment(Fragment fragment,int fragmentno){
        if(fragmentno != current_fragment){// if the user is in same fragment
            current_fragment = fragmentno;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out); //animation effects in fragments
            fragmentTransaction.replace(frameLayout.getId(),fragment);
            fragmentTransaction.commit();
        }

    }

}