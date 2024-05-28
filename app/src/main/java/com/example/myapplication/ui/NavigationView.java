package com.example.myapplication.ui;

import android.view.MenuItem;

import androidx.annotation.NonNull;

public interface NavigationView extends com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener {
    boolean onNavigationItemSelected(@NonNull MenuItem item);
}
