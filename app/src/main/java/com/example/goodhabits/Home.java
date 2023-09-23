package com.example.goodhabits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    Button logOut = null;
    TextView greet = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        greet = findViewById(R.id.greeting);
        logOut = (Button)findViewById(R.id.btn_log_out);
        greet.setText("Hello "+getIntent().getStringExtra("user_name"));
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getResources().getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleClient = GoogleSignIn.getClient(Home.this, options);
                googleClient.signOut();
                SharedPreferences prefs = getSharedPreferences("user-Storage", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", null);
                editor.putString("name", null);
                editor.putString("idToken",null);
                editor.putBoolean("hasLogin",false);
                editor.apply();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG","onAuthStateChanged:signed_out");

                }
                Intent back = new Intent(Home.this,MainActivity.class);
                startActivity(back);
            }
        });
    }

}
