package com.annekay.android.androiddevelopers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DevelopersListFragment())
                .commit();
    }
}
