package com.example.tm4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView loginSuccessTextView, loginResultTextView;
    private CardView loginResultCardView;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        loginSuccessTextView = view.findViewById(R.id.loginSuccessTextView);
        loginResultTextView = view.findViewById(R.id.loginResultTextView);
        loginResultCardView = view.findViewById(R.id.loginResultCardView);

        sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check login credentials
                String savedUsername = sharedPreferences.getString("username", "");
                String savedPassword = sharedPreferences.getString("password", "");
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                if (savedUsername.equals(enteredUsername) && savedPassword.equals(enteredPassword)) {
                    // Display login success
                    showLoginResult(enteredUsername, enteredPassword);
                } else {
                    Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void showLoginResult(String username, String password) {
        loginSuccessTextView.setVisibility(View.VISIBLE);

        String loginResult = "Username: " + username + "\nPassword: " + password;
        loginResultTextView.setText(loginResult);

        loginResultTextView.setVisibility(View.VISIBLE);

        loginResultCardView.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginResultTextView.setVisibility(View.GONE);
                loginSuccessTextView.setVisibility(View.GONE);
                loginResultCardView.setVisibility(View.GONE);
            }
        }, 5000);
    }

}


