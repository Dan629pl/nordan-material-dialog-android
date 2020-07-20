package com.nordan.sampleapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.nordan.dialog.Animation;
import com.nordan.dialog.NordanAlertDialog;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DialogCreator extends AppCompatActivity {

    //Dialog controllers
    private Spinner spinnerAnimation;
    private TextInputEditText editTextPosBtn;
    private TextInputEditText editTextNegBtn;
    private TextInputEditText editTextTittle;
    private TextInputEditText editTextMessage;
    private MaterialCheckBox checkBoxCancellable;
    private ImageView imageViewColorPicker;
    private ImageView imageViewAccentColor;

    //Dialog config
    private boolean cancellable;
    private String btnPos;
    private String btnNeg;
    private Animation animation;
    private int headerColor;
    private int accentColor;
    private int icon;
    private boolean isGif;
    private String tittle;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_creator_activity);
        initControllers();
        setupControllers();
    }

    private void setupControllers() {
        final List<String> animations = Arrays.stream(Animation.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
        animations.add(0, getString(R.string.none));
        ArrayAdapter<String> animationListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, animations);
        spinnerAnimation.setAdapter(animationListAdapter);
        imageViewColorPicker.setOnClickListener(click -> showHeaderColorPicker());
        imageViewAccentColor.setOnClickListener(click -> showAccentColorPicker());
        imageViewAccentColor.setBackgroundColor(getColor(R.color.colorPrimary));
    }

    private void showAccentColorPicker() {
        new ColorPickerDialog.Builder(this)
                .setPositiveButton("Pick", (ColorEnvelopeListener) (envelope, fromUser) -> setupAccentDialogColor(envelope.getColor()))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(true)
                .attachBrightnessSlideBar(true)
                .show();
    }

    private void setupAccentDialogColor(int color) {
        accentColor = color;
        imageViewAccentColor.setBackgroundColor(color);
    }

    private void showHeaderColorPicker() {
        new ColorPickerDialog.Builder(this)
                .setPositiveButton("Pick", (ColorEnvelopeListener) (envelope, fromUser) -> setupHeaderDialogColor(envelope.getColor()))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(true)
                .attachBrightnessSlideBar(true)
                .show();
    }

    private void setupHeaderDialogColor(int color) {
        headerColor = color;
        imageViewColorPicker.setBackgroundColor(color);
    }

    private void initControllers() {
        spinnerAnimation = findViewById(R.id.spinner_animation);
        findViewById(R.id.btn_reset).setOnClickListener(click -> clearDialogCreator());
        findViewById(R.id.btn_show).setOnClickListener(click -> showDialog());
        editTextPosBtn = findViewById(R.id.text_pos_btn);
        editTextNegBtn = findViewById(R.id.text_neg_btn);
        editTextTittle = findViewById(R.id.text_dialog_tittle);
        editTextMessage = findViewById(R.id.text_dialog_message);
        checkBoxCancellable = findViewById(R.id.checkbox_cancellable);
        imageViewColorPicker = findViewById(R.id.image_header_color);
        imageViewAccentColor = findViewById(R.id.accent_color);
    }

    private void clearDialogCreator() {
        editTextPosBtn.getText().clear();
        editTextNegBtn.getText().clear();
        editTextTittle.getText().clear();
        editTextMessage.getText().clear();
        checkBoxCancellable.setChecked(false);
        spinnerAnimation.setSelection(0);
        imageViewColorPicker.setBackgroundColor(getColor(R.color.transparent));
        headerColor = 0;
        imageViewAccentColor.setBackgroundColor(getColor(R.color.colorPrimary));
        accentColor = 0;
        editTextPosBtn.clearFocus();
        editTextNegBtn.clearFocus();
        editTextTittle.clearFocus();
        editTextMessage.clearFocus();
    }

    private void showDialog() {
        setupDialog();
        new NordanAlertDialog.Builder(this)
                .setTitle(tittle)
                .setMessage(message)
                .setPositiveBtnText(btnPos)
                .setNegativeBtnText(btnNeg)
                .setAnimation(animation)
                .onPositiveClicked(() -> Toast.makeText(this, "Clicked positive", Toast.LENGTH_SHORT).show())
                .onNegativeClicked(() -> Toast.makeText(this, "Clicked negative", Toast.LENGTH_SHORT).show())
                .isCancellable(cancellable)
                .setHeaderColor(headerColor)
                .setDialogAccentColor(accentColor)
                .setIcon(icon, isGif)
                .build().show();
    }

//    private void showLoadingDialog() {
//        Dialog loading = NordanLoadingDialog.createLoadingDialog(this, "Logowanie…");
//        loading.show();
//        new Handler().postDelayed(loading::hide, 3000);
//    }

    private void setupDialog() {
        btnPos = editTextPosBtn.getText().toString();
        btnNeg = editTextNegBtn.getText().toString();
        tittle = editTextTittle.getText().toString();
        message = editTextMessage.getText().toString();
        cancellable = checkBoxCancellable.isChecked();
        String selectedAnimation = (String) spinnerAnimation.getSelectedItem();
        animation = selectedAnimation.equalsIgnoreCase(getString(R.string.none)) ? null : Animation.valueOf(selectedAnimation);
    }
}
