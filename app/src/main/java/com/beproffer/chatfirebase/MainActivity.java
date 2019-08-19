package com.beproffer.chatfirebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.beproffer.chatfirebase.fragments.ChatFragment;
import com.beproffer.chatfirebase.fragments.ListUsersFragment;
import com.beproffer.chatfirebase.fragments.RegistrationFragment;


import static com.beproffer.chatfirebase.MainActivity.MyFragmets.RegistrationFragment;
import static com.beproffer.chatfirebase.MainActivity.MyFragmets.ListUsersFragment;
import static com.beproffer.chatfirebase.MainActivity.MyFragmets.ChatFragment;

public class MainActivity extends AppCompatActivity {


    public enum MyFragmets {RegistrationFragment, ListUsersFragment, ChatFragment}

    public Fragment registrationFragment, listUsersFragment, chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrationFragment = new RegistrationFragment();
        listUsersFragment = new ListUsersFragment();
        chatFragment = new ChatFragment();


        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragment_container, registrationFragment)
                .commit();

    }

    public void ChangeFragment(MyFragmets changefragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (changefragment) {
            case RegistrationFragment:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, registrationFragment)
                        .commit();
                break;
            case ListUsersFragment:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, listUsersFragment)
                        .commit();
                break;
            case ChatFragment:
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, chatFragment)
                        .commit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            if (resultCode == MainActivity.RESULT_CANCELED) {
                ChangeFragment(ListUsersFragment);

            } else if (resultCode == MainActivity.RESULT_OK)
                ChangeFragment(ChatFragment);
            else
                ChangeFragment(RegistrationFragment);
        }
    }
}
