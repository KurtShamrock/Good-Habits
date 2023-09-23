package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    FirebaseAuth auth;
    FirebaseDatabase db;
    GoogleSignInClient GSIC;
    int RC_SIGN_IN = 26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedRef = MainActivity.this.getSharedPreferences("user-Storage",Context.MODE_PRIVATE);
        if(sharedRef.getBoolean("hasLogin",false)==true){
            goToNextActivity();
        }
        loginBtn = findViewById(R.id.btnGGLogin);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GSIC = GoogleSignIn.getClient(this,gso);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }
    private void googleSignIn(){
        Intent intent = GSIC.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }
    private void goToNextActivity(){
        Intent intent = new Intent(MainActivity.this, Home.class);
        SharedPreferences prefs = getSharedPreferences("user-Storage", MODE_PRIVATE);
        intent.putExtra("user_name",prefs.getString("name",""));
        intent.putExtra("id_token", prefs.getString("idToken",""));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount  acc = task.getResult(ApiException.class);
                Log.i("Tag",acc.getEmail());
                firebaseAuth(acc.getIdToken());
            }
            catch(Exception e)
            {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("ERR",e.toString());
            }

        }
    }

    private void firebaseAuth(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {

                                               if(task.isSuccessful()){
                                                   FirebaseUser user = auth.getCurrentUser();
                                                   SharedPreferences prefs = getSharedPreferences("user-Storage", MODE_PRIVATE);
                                                   SharedPreferences.Editor editor = prefs.edit();
                                                   editor.putString("email", user.getEmail());
                                                   editor.putString("name", user.getDisplayName());
                                                   editor.putString("idToken",idToken);
                                                   editor.putBoolean("hasLogin",true);
                                                   editor.apply();
                                                   HashMap<String,Object> map = new HashMap<>();
                                                   map.put("id",user.getUid());
                                                   map.put("name",user.getDisplayName());
                                                   map.put("profile",user.getPhotoUrl().toString());
                                                   db.getReference().child("users").child(user.getUid()).setValue(map);

                                                   goToNextActivity();
                                               }
                                               else {
                                                   Toast.makeText(MainActivity.this, "something wrong!", Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       }
                );
    }
}