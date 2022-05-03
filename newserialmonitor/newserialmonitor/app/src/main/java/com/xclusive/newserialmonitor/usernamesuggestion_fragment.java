package com.xclusive.newserialmonitor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link usernamesuggestion_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class usernamesuggestion_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public usernamesuggestion_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment usernamesuggestion_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static usernamesuggestion_fragment newInstance(String param1, String param2) {
        usernamesuggestion_fragment fragment = new usernamesuggestion_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private AutoCompleteTextView autoedittextview;
    private EditText name;
    private FloatingActionButton activitybtn;
    private FirebaseFirestore firebaseFirestore;
    public ArrayList suggestionlist;
    public long index;
    public long size1;
    public static boolean user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usernamesuggestion, container, false);
        activitybtn = view.findViewById(R.id.activitbtn);
        autoedittextview = view.findViewById(R.id.autotext);

        autoedittextviewmethod();

        autoedittextview.addTextChangedListener(btncheck);
        activitybtn.setEnabled(false);
        //autoedittextview.findFocus();


        activitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validname()){
                    return;
                }
                else {
                    loaduser();
                    getFragmentManager().beginTransaction().replace(R.id.fragment, new devicefragment(), "Devise List").addToBackStack(null).commit();
                }

            }

        });
        return view;
    }

    private void loaduser() {

        firebaseFirestore = FirebaseFirestore.getInstance();
        String a = autoedittextview.getText().toString();
        if(suggestionlist.contains(a)){
            user = false;
        }
        else {
            user =true;
        }

        if (user){
            Map<String ,Object> user = new HashMap<>();
            user.put(String.valueOf(index+1),autoedittextview.getText().toString());
            firebaseFirestore.collection("SUGGESTION").document("USERNAMES").update(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                return;
                            }
                            else
                            {
                                Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void autoedittextviewmethod() {
        suggestionlist = new ArrayList<String>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("SUGGESTION").document("USERNAMES").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            size1 = task.getResult().getData().size();

                            // Toast.makeText(activity_1.this,String.valueOf(size1),Toast.LENGTH_SHORT).show();

                            for (long i=1;i<=size1;i++){
                                if (task.getResult().get(String.valueOf(i)).toString().isEmpty()){

                                    Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else {
                                    suggestionlist.add(task.getResult().get(String.valueOf(i)).toString());
                                    index = i;
                                }
                            }


                        }
                        else {
                            Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String >(getContext(),
                android.R.layout.simple_list_item_1,suggestionlist);


        autoedittextview.setAdapter(arrayAdapter);
    }
    public boolean validname(){
        if(autoedittextview.getText().toString().isEmpty()){
            autoedittextview.setError("Empty!");
            return false;
        }
        else if(autoedittextview.getText().toString().length() <3) {
            autoedittextview.setError("Invalid Name");
            return false;
        }
        else {
            return true;
        }
    }
    private TextWatcher btncheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String a = autoedittextview.getText().toString().trim();
            activitybtn.setEnabled(!a.isEmpty() );


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}