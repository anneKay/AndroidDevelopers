package com.annekay.android.androiddevelopers;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SlidingPaneLayout pane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DevelopersListFragment())
                .commit();

    }

}
