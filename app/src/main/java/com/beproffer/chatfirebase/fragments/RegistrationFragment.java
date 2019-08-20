package com.beproffer.chatfirebase.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.beproffer.chatfirebase.MainActivity;
import com.beproffer.chatfirebase.R;
import com.beproffer.chatfirebase.users.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;
import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {


    private static final String TAG = "GOOGLE";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText edEmail;
    private EditText edPassword;
    private EditText edEmailReg;
    private EditText edPasswordReg;
    private EditText edNameReg;
    private Button btnSign;
    private Button btnRegister;

    private Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_registration, container, false);



        edEmail = (EditText) v.findViewById(R.id.et_email);
        edPassword = (EditText) v.findViewById(R.id.et_password);

        edEmailReg = (EditText) v.findViewById(R.id.et_email_reg);
        edPasswordReg = (EditText) v.findViewById(R.id.et_password_reg);
        edNameReg = (EditText) v.findViewById(R.id.et_name_reg);

        btnSign = (Button) v.findViewById(R.id.btn_sign_in);
        btnRegister = (Button) v.findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {

            ((MainActivity) getActivity()).ChangeFragment(MainActivity.MyFragmets.ListUsersFragment);
        }

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edEmail.getText().toString().equals("") &
                        !edPassword.getText().toString().equals("")) {

                    final String email = edEmail.getText().toString().trim();
                    String password = edPassword.getText().toString().trim();

                    signing(email, password);
                } else {
                    Toast.makeText(getContext(), R.string.empty_email_or_password, Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edEmailReg.getText().toString().equals("") ||
                        !edPasswordReg.getText().toString().equals("") ||
                        !edNameReg.getText().toString().equals(""))


                {
                    registration();
                } else {
                    Toast.makeText(getContext(), R.string.empty_email_or_password, Toast.LENGTH_SHORT).show();
                }

            }
        });




        return v;


    }



    private void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    ((MainActivity) getActivity()).ChangeFragment(MainActivity.MyFragmets.ListUsersFragment);

                } else {

                    Toast.makeText(getContext(), R.string.autorithation, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void registration(){

        final String name = edNameReg.getText().toString().trim();
        final String email = edEmailReg.getText().toString().trim();
        String password = edPasswordReg.getText().toString().trim();

        if (name.isEmpty()) {
            edNameReg.setError(getText(R.string.empty_name));
            edNameReg.requestFocus();
            return;
        }
        if (!isNameValid(edNameReg.getText().toString())) {
            edNameReg.setError(getText(R.string.not_valid_name));
            edNameReg.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edEmailReg.setError(getText(R.string.empty_email));
            edEmailReg.requestFocus();
            return;
        }
        if (!isEmailValid(edEmailReg.getText().toString())) {
            edEmailReg.setError(getText(R.string.not_valid_email));
            edEmailReg.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edPasswordReg.setError(getText(R.string.password_empty));
            edPasswordReg.requestFocus();
            return;
        }
        if (!isPasswordValid(edPasswordReg.getText().toString())) {
            edPasswordReg.setError(getText(R.string.password_not_valid));
            edPasswordReg.requestFocus();
            return;
        }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                UserData user = new UserData(
                                        name,
                                        email
                                );

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            //backToBack();
                                            Toast.makeText(getContext(), R.string.valid, Toast.LENGTH_SHORT).show();


                                        } else {

                                            Toast.makeText(getContext(), R.string.valid, Toast.LENGTH_SHORT).show();
                                            //display a failure message
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


    }
    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX =
                Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
                        Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        final Pattern PASSWORD_REGEX =
                Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
                        Pattern.CASE_INSENSITIVE);
        return PASSWORD_REGEX.matcher(password).matches();
    }
    /////////////////т.е. разрешены все английские и русские буквы, цифры, символ нижнего подчеркивания и пробел. Всё остальное запрещено.
    public static boolean isNameValid(String name) {
        final Pattern PASSWORD_REGEX =
                Pattern.compile("^[_a-zA-Z0-9абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ]+$",
                        Pattern.CASE_INSENSITIVE);
        return PASSWORD_REGEX.matcher(name).matches();
    }

}

