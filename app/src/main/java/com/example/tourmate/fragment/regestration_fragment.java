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
import com.example.tourmate.pojos.UserProfileINFO;
import com.example.tourmate.viewmodels.LoginViewModel;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class regestration_fragment extends Fragment {

    private EditText nameET,emailET,passwordET,ConPassET;
    private Button registerbtn;
    private TextView membershipTV;
    private boolean isPasswordVisible;
    private ImageView togglepassVisible,togglepassInvisible,toggleConpassVisible,toggleConpassInvisible;
    private LoginViewModel loginViewModel;


    public regestration_fragment() {
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
        togglepassVisible = view.findViewById(R.id.toogleVisibleRepassword);
        togglepassInvisible = view.findViewById(R.id.toogleInvisibleRepassword);
        toggleConpassVisible = view.findViewById(R.id.toogleVisibleReConpassword);
        toggleConpassInvisible = view.findViewById(R.id.toogleInvisibleConRepassword);

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

        togglepassVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePassword();
            }
        });
        togglepassInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePassword();
            }
        });
        toggleConpassVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleConPassword();
            }
        });
        toggleConpassInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleConPassword();
            }
        });
    }

    private void toggleConPassword() {
        if (isPasswordVisible) {
            String pass = ConPassET.getText().toString();
            ConPassET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ConPassET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ConPassET.setText(pass);
            ConPassET.setSelection(pass.length());
            togglepassVisible.setVisibility(GONE);
            togglepassInvisible.setVisibility(View.VISIBLE);
        } else {
            String pass = ConPassET.getText().toString();
            ConPassET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ConPassET.setInputType(InputType.TYPE_CLASS_TEXT);
            ConPassET.setText(pass);
            ConPassET.setSelection(pass.length());
            togglepassVisible.setVisibility(View.VISIBLE);
            togglepassInvisible.setVisibility(GONE);
        }
        isPasswordVisible= !isPasswordVisible;
    }

    private void togglePassword() {

        if (isPasswordVisible) {
            String pass = passwordET.getText().toString();
            passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordET.setText(pass);
            passwordET.setSelection(pass.length());
            togglepassVisible.setVisibility(GONE);
            togglepassInvisible.setVisibility(View.VISIBLE);
        } else {
            String pass = passwordET.getText().toString();
            passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordET.setInputType(InputType.TYPE_CLASS_TEXT);
            passwordET.setText(pass);
            passwordET.setSelection(pass.length());
            togglepassVisible.setVisibility(View.VISIBLE);
            togglepassInvisible.setVisibility(GONE);
        }
        isPasswordVisible= !isPasswordVisible;

    }
}
