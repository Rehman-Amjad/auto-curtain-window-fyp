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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technogenis.autocurtainwindow.R;

import java.io.ByteArrayOutputStream;


public class RoomFragment extends Fragment {


    TextView date_text,time_text,humidityValue_text,temp_text,ldr_text,id_text,gas_text;
    ImageView image;

    String date,time,imageUrl,humidity,temperature,ldr,gas,id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        init(view);
        currentValue();

        return view;
    }

    void init(View view){
        date_text = view.findViewById(R.id.date_text);
        time_text = view.findViewById(R.id.time_text);
        image = view.findViewById(R.id.image);
        humidityValue_text = view.findViewById(R.id.humidityValue_text);
        temp_text = view.findViewById(R.id.temp_text);
        ldr_text = view.findViewById(R.id.ldr_text);
        id_text = view.findViewById(R.id.id_text);
        gas_text = view.findViewById(R.id.gas_text);
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
                    date = snapshot.child("Dated").getValue(String.class);
                    time = snapshot.child("Timed").getValue(String.class);
                    imageUrl = snapshot.child("Img").getValue(String.class);
                    humidity = snapshot.child("Humidity").getValue(String.class);
                    temperature = snapshot.child("Temperature").getValue(String.class);
                    ldr = snapshot.child("LDR").getValue(String.class);
                    gas = snapshot.child("Gas").getValue(String.class);
                    id = snapshot.child("Id").getValue(String.class);

                    showImage(imageUrl);
                    date_text.setText("Date: "+date);
                    time_text.setText("Time: "+time);
                    humidityValue_text.setText("Humidity: "+humidity);
                    temp_text.setText("Temperature: "+temperature);
                    ldr_text.setText("LDR: "+ldr);
                    id_text.setText("ID: "+id);
                    gas_text.setText("Gas: "+gas);

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