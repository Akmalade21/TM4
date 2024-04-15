package com.example.tm4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RegisterFragment extends Fragment {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private CheckBox termsCheckbox;
    private Button createAccountButton;
    private TextView registerSuccessTextView, registerResultTextView;
    private CardView registerResultCardView;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        termsCheckbox = view.findViewById(R.id.termsCheckbox);
        createAccountButton = view.findViewById(R.id.createAccountButton);
        registerSuccessTextView = view.findViewById(R.id.registerSuccessTextView);
        registerResultTextView = view.findViewById(R.id.registerResultTextView);
        registerResultCardView = view.findViewById(R.id.registerResultCardView);

        usernameEditText.addTextChangedListener(textWatcher);
        emailEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
        termsCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllFields();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllFields()) {
                    saveUserToSharedPreferences(
                            usernameEditText.getText().toString().trim(),
                            passwordEditText.getText().toString().trim()
                    );

                    showRegisterSuccess(
                            usernameEditText.getText().toString().trim(),
                            emailEditText.getText().toString().trim(),
                            passwordEditText.getText().toString().trim()
                    );

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentManager fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction().remove(RegisterFragment.this).commit();
                            fragmentManager.popBackStack();
                        }
                    }, 5000);
                }
            }
        });

        createAccountButton.setEnabled(false);

        return view;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do nothing
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkAllFields();
        }
    };

    private boolean checkAllFields() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean isChecked = termsCheckbox.isChecked();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && isChecked) {
            createAccountButton.setEnabled(true);
            return true;
        } else {
            createAccountButton.setEnabled(false);
            return false;
        }
    }

    private void saveUserToSharedPreferences(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void showRegisterSuccess(String username, String email, String password) {
        registerSuccessTextView.setVisibility(View.VISIBLE);

        registerResultTextView.setText("Username: " + username + "\nEmail: " + email + "\nPassword: " + password);
        registerResultCardView.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registerSuccessTextView.setVisibility(View.GONE);
                registerResultCardView.setVisibility(View.GONE);
            }
        }, 5000);
    }
}
