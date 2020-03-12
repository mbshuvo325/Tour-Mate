package com.example.tourmate.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourmate.R;
import com.example.tourmate.viewmodels.LoginViewModel;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_Fragment extends Fragment {

    private EditText usernameET;
    private EditText passwordET;
    private Button loginbtn;
    private Button registerbtn;
    private TextView statusTv;
    private boolean isPasswordVisible;
    private ImageView toggleVisible,toggleInvisible;
    private LoginViewModel loginViewModel;

    public Login_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        (getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameET = view.findViewById(R.id.loginUserName);
        passwordET = view.findViewById(R.id.loginPassword);
        loginbtn = view.findViewById(R.id.loginbtn);
        registerbtn = view.findViewById(R.id.register);
        statusTv = view.findViewById(R.id.status);
        toggleVisible = view.findViewById(R.id.toogleVisiblepassword);
        toggleInvisible = view.findViewById(R.id.toogleInvisiblepassword);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                loginViewModel.loginuser(email,password);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String email = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                UserProfileINFO userInfo = new UserProfileINFO(null,name,email,password);
                loginViewModel.registeruser(userInfo);*/
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.regestration_fragment);
            }
        });
        loginViewModel.stateLiveData.observe(this, new Observer<LoginViewModel.AuthenticationState>() {
            @Override
            public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
               switch (authenticationState){
                   case AUTHENTICATED:
                        Navigation.findNavController(view).navigate(R.id.action_login_Fragment_to_event_List);
                       break;
                   case UNAUTHENTICATED:
                       statusTv.setText("Login With Valid Email & Password");
                       break;
               }
            }
        });

        loginViewModel.errormessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                statusTv.setText(s);
            }
        });

        toggleVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tooglePassword();
            }
        });
        toggleInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tooglePassword();
            }
        });
    }

    private void tooglePassword() {

        if (isPasswordVisible) {
            String pass = passwordET.getText().toString();
            passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordET.setText(pass);
            passwordET.setSelection(pass.length());
            toggleVisible.setVisibility(GONE);
            toggleInvisible.setVisibility(View.VISIBLE);
        } else {
            String pass = passwordET.getText().toString();
            passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordET.setInputType(InputType.TYPE_CLASS_TEXT);
            passwordET.setText(pass);
            passwordET.setSelection(pass.length());
            toggleVisible.setVisibility(View.VISIBLE);
            toggleInvisible.setVisibility(GONE);
        }
        isPasswordVisible= !isPasswordVisible;
    }
}
