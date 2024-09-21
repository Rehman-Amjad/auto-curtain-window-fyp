package com.technogenis.autocurtainwindow.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technogenis.autocurtainwindow.R;

import java.io.ByteArrayOutputStream;

public class HumidityFragment extends Fragment {

    TextView humidityValue_text,date_text,time_text;
    EditText ed_humidity;
    Button btnSave;
    ImageView image;

    FirebaseDatabase database;
    DatabaseReference myRef;

    String humidityValue,date,time,imageUrl,autoValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_humidity, container, false);


        init(view);
        currentValue();
        automaticValue();

        btnSave.setOnClickListener(v -> {
            // Get the reference to the specific location in Firebase
            DatabaseReference callRef = FirebaseDatabase.getInstance()
                    .getReference("Automatic");

            callRef.child("SetHumidity").setValue(ed_humidity.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
                            ed_humidity.setText("");
                        } else {
                            // If the task fails, log the error or notify the user
                            Toast.makeText(getActivity(), "Failed to save data", Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        return  view;
    }

    void init(View view){
        humidityValue_text = view.findViewById(R.id.humidityValue_text);
        date_text = view.findViewById(R.id.date_text);
        time_text = view.findViewById(R.id.time_text);
        ed_humidity = view.findViewById(R.id.ed_humidity);
        btnSave = view.findViewById(R.id.btnSave);
        image = view.findViewById(R.id.image);
    }

    private void currentValue() {
        DatabaseReference callRef = FirebaseDatabase.getInstance()
                .getReference("CurrentInfo")
                .child("1000");

        callRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    humidityValue = snapshot.child("Humidity").getValue(String.class);
                    date = snapshot.child("Dated").getValue(String.class);
                    time = snapshot.child("Timed").getValue(String.class);
                    imageUrl = snapshot.child("Img").getValue(String.class);

                    humidityValue_text.setText("Humidity sensor value: "+humidityValue);
                    showImage(imageUrl);
                    date_text.setText("Date: "+date);
                    time_text.setText("Time: "+time);
//                    ed_humidity.setText(humidityValue);

                } else {
                    Log.w("Firebase", "Data snapshot doesn't exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Data fetch cancelled", error.toException());
            }
        });
    }

    private void automaticValue() {
        DatabaseReference callRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Automatic");

        callRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    autoValue = snapshot.child("SetHumidity").getValue(String.class);

                    ed_humidity.setText(autoValue);


                } else {
                    Log.w("Firebase", "Data snapshot doesn't exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Data fetch cancelled", error.toException());
            }
        });
    }


    void showImage(String imageUrl){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(imageUrl, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        image.setImageBitmap(decodedImage);
    }

}