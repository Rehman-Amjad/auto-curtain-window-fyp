package com.technogenis.autocurtainwindow.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.technogenis.autocurtainwindow.MainActivity;
import com.technogenis.autocurtainwindow.R;

public class LoginScreen extends AppCompatActivity {

    ImageView imageView;
    Button btnLogin,btnExit;
    EditText etUserName,etUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);


        btnLogin=findViewById(R.id.btnLogin);
        btnExit=findViewById(R.id.btnExit);
        etUserName=findViewById(R.id.etUserName);
        etUserPassword=findViewById(R.id.etUserPassword);
        imageView=findViewById(R.id.imageView);

        String username="Admin";
        String userpassword="asdf@1234";

        btnExit.setOnClickListener(v -> {
            System.exit(0);
        });

        btnLogin.setOnClickListener(v -> {

            if (username.equals(etUserName.getText().toString()))
            {
                if (userpassword.equals(etUserPassword.getText().toString()))
                {
                    Intent intent=new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    etUserPassword.setError("incorrect Password");
                    etUserPassword.requestFocus();
                    etUserPassword.setText("");
                }
            }
            else
            {
                etUserName.setError("incorrect username");
                etUserName.requestFocus();
                etUserName.setText("");
            }

        });


    }
}