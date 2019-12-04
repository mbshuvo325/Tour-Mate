package com.example.tourmate.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.tourmate.R;
import com.example.tourmate.viewmodels.LoginViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_Fragment extends Fragment {

    private EditText usernameET;
    private EditText passwordET;
    private Button loginbtn;
    private Button registerbtn;
    private TextView statusTv;
    private LoginViewModel loginViewModel;

    public Login_Fragment() {
        // Required empty public constructor
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
                String email = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                loginViewModel.registeruser(email,password);
               /// Navigation.findNavController(view).navigate(R.id.register_Fragment);
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
                       statusTv.setText("Login Unsuccessful Try Again");
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
    }
}
