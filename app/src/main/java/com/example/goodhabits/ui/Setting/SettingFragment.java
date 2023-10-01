package com.example.goodhabits.ui.Setting;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.goodhabits.viewmodel.DataViewModel;
import com.example.goodhabits.MainActivity;
import com.example.goodhabits.R;
import com.example.goodhabits.model.User;
import com.example.goodhabits.model.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment {

    private CircleImageView imageUser;
    private TextView tvFullName;
    private AppCompatEditText edtName, edtEmail;
    private ImageView imgEditIcon;
    private AppCompatButton btnLogout, btnDeleteAccount;
    private User user;
    private UserInfo userInfo;
    private DataViewModel dataViewModel;
    private LiveData<String> fullName;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        tvFullName = rootView.findViewById(R.id.tvFullNameSetting);
        edtName = rootView.findViewById(R.id.edtFullNameSetting);
        edtEmail = rootView.findViewById(R.id.edtEmailSetting);
        imgEditIcon = rootView.findViewById(R.id.imgEditSetting);
        imageUser = rootView.findViewById(R.id.imgUserSetting);
        btnLogout = rootView.findViewById(R.id.btnLogout);
        btnDeleteAccount = rootView.findViewById(R.id.btnDeleteAccount);

        dataViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        fullName = dataViewModel.getUserName();
        user = dataViewModel.getUser();
        userInfo = dataViewModel.getUserInfo();

        tvFullName.setText(user.getFullName());
        edtName.setText(user.getFullName());
        edtEmail.setText(user.getEmail());
        edtEmail.setEnabled(false);
        //
        String url = userInfo.getPathToImageFile();
        Glide.with(this).load(url).centerCrop().into(imageUser);
        //
        editInfo();
        signOut();
        deleteAccount();
        fullName.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvFullName.setText(s);
                user.setFullName(s);
            }
        });

        return rootView;
    }

    public void editInfo() {
        imgEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                dataViewModel.setUserName(name);
                Toast.makeText(getContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteAccount() {
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Confirm");
                builder.setMessage("Bạn có chắc chắn xóa tài khoản?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void signOut() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getResources().getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleClient = GoogleSignIn.getClient(getActivity(), options);
                googleClient.signOut();
                prefs = getContext().getSharedPreferences("user-Storage", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", null);
                editor.putString("name", null);
                editor.putString("idToken", null);
                editor.putBoolean("hasLogin", false);
                editor.apply();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");

                }
                Intent back = new Intent(getActivity(), MainActivity.class);
                startActivity(back);
            }
        });
    }

}