package com.databasefirestorelogin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class DatabaseFIrestoreProfile extends Fragment {
    EditText etEmail, etpassword, etName, etDob, etCity;
    ProgressDialog loadingBar;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    String userId;
    RadioGroup radioGroup;
    RadioButton radioMale, radioFemale;


    public DatabaseFIrestoreProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.window_my_profile, viewGroup, false);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etpassword = (EditText) view.findViewById(R.id.etpassword);
        etName = (EditText) view.findViewById(R.id.etName);
        etDob = (EditText) view.findViewById(R.id.etDob);
        etCity = (EditText) view.findViewById(R.id.etCity);


        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioMale = (RadioButton)view. findViewById(R.id.radioMale);
        radioFemale = (RadioButton)view. findViewById(R.id.radioFemale);
        getUserData();
        return view;
    }

    public void getUserData() {
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("UsersDb").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    etEmail.setText(documentSnapshot.getString("db_password"));
                    etName.setText(documentSnapshot.getString("db_name"));
                    etpassword.setText(documentSnapshot.getString("db_email"));
                    etCity.setText(documentSnapshot.getString("db_city"));
                    etDob.setText(documentSnapshot.getString("db_birthdate"));
                    if(documentSnapshot.getString("db_gender").equals("Male")){
                        radioMale.setChecked(true);
                    }
                    else {
                        radioFemale.setChecked(true);
                    }


                } else {
                    Toast.makeText(getContext(), "Somethig went wrong please try again later", Toast.LENGTH_SHORT).show();
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

    }
}