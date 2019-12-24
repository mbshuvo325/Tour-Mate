package com.example.tourmate.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tourmate.R;
import com.example.tourmate.pojos.UserProfileINFO;
import com.example.tourmate.viewmodels.LoginViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class regestration_fragment extends Fragment {

    private EditText nameET,emailET,passwordET,ConPassET;
    private Button registerbtn;
    private TextView membershipTV;
    private LoginViewModel loginViewModel;


    public regestration_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        return inflater.inflate(R.layout.fragment_regestration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameET = view.findViewById(R.id.nameIDET);
        emailET = view.findViewById(R.id.uerEmail);
        passwordET = view.findViewById(R.id.uerNewPassword);
        ConPassET = view.findViewById(R.id.uerConfirmPassword);
        registerbtn = view.findViewById(R.id.registerUser);
        membershipTV = view.findViewById(R.id.gotoLogin);

        membershipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.login_Fragment);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String conPass = ConPassET.getText().toString();
                if (name.isEmpty()) {
                    nameET.setError("Provide a name");
                } else if (email.isEmpty()) {
                    emailET.setError("Provide a email");
                } else if (password.isEmpty()) {
                    passwordET.setError("Provide a password");
                }
                else if (password.length() < 6) {
                    passwordET.setError("Set minimum six digits length password");
                }
                else if (password.equals(conPass)) {
                    UserProfileINFO userProfileINFO = new UserProfileINFO(null, name, email, password);
                    loginViewModel.registeruser(userProfileINFO);
                }
                else {

                    ConPassET.setError("Password not match!");
                }
            }
        });

        loginViewModel.stateLiveData.observe(this, new Observer<LoginViewModel.AuthenticationState>() {
            @Override
            public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
                switch (authenticationState)
                {
                    case AUTHENTICATED:
                        Navigation.findNavController(view).navigate(R.id.event_List);
                        break;
                    case UNAUTHENTICATED:
                        break;
                }
            }
        });
    }
}
