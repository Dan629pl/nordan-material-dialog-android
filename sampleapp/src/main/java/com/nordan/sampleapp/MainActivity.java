package com.nordan.sampleapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.btn_create).setOnClickListener(click -> showDialogCreator());
        findViewById(R.id.btn_default).setOnClickListener(click -> showDefaultDialogs());
    }

    private void showDialogCreator() {
        startActivity(new Intent(this, DialogCreator.class));
    }

    private void showDefaultDialogs() {
        startActivity(new Intent(this, DefaultDialog.class));
    }
}
