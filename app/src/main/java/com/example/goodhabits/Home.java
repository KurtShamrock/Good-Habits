package com.example.goodhabits;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.goodhabits.databinding.ActivityHomeBinding;
import com.example.goodhabits.model.MeditatingHistory;
import com.example.goodhabits.model.ReadingHistory;
import com.example.goodhabits.model.Todo;
import com.example.goodhabits.model.User;
import com.example.goodhabits.model.UserInfo;
import com.example.goodhabits.model.WoHistory;
import com.example.goodhabits.ui.Greeting.GreetingFragment;
import com.example.goodhabits.ui.Setting.SettingFragment;
import com.example.goodhabits.ui.profile.ProfileFragment;
import com.example.goodhabits.viewmodel.DataViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {
    ActivityHomeBinding binding;
    private User user;
    private UserInfo userInfo;
    private Todo todo;
    private WoHistory woHistory;
    private ReadingHistory readingHistory;
    private MeditatingHistory meditatingHistory;
    private DataViewModel dataViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new GreetingFragment());
        binding.navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(new GreetingFragment());
                        break;
                    case R.id.setting:
                        replaceFragment(new SettingFragment());
                        break;
                    case R.id.profile:
                        replaceFragment(new ProfileFragment());
                        break;
                }
                return true;
            }
        });

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        user = new User();
        userInfo = new UserInfo();
        todo = new Todo();
        meditatingHistory = new MeditatingHistory();
        woHistory = new WoHistory();
        readingHistory = new ReadingHistory();
        // T
        user.setFullName(getIntent().getStringExtra("user_name"));
        user.setEmail(getIntent().getStringExtra("email"));
        //
        dataViewModel.setUser(user);
        dataViewModel.setUserInfo(userInfo);
        dataViewModel.setTodo(todo);
        dataViewModel.setWoHistory(woHistory);
        dataViewModel.setReadingHistory(readingHistory);
        dataViewModel.setMeditatingHistory(meditatingHistory);
        dataViewModel.setLastCheckDay(System.currentTimeMillis());

//        greet = findViewById(R.id.greeting);
//        logOut = (Button)findViewById(R.id.btn_log_out);
//        greet.setText("Hello "+getIntent().getStringExtra("user_name"));
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getResources().getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build();
//
//                GoogleSignInClient googleClient = GoogleSignIn.getClient(Home.this, options);
//                googleClient.signOut();
//                SharedPreferences prefs = getSharedPreferences("user-Storage", MODE_PRIVATE);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putString("email", null);
//                editor.putString("name", null);
//                editor.putString("idToken",null);
//                editor.putBoolean("hasLogin",false);
//                editor.apply();
//                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d("TAG","onAuthStateChanged:signed_out");
//
//                }
//                Intent back = new Intent(Home.this,MainActivity.class);
//                startActivity(back);
//            }
//        });
//

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}

