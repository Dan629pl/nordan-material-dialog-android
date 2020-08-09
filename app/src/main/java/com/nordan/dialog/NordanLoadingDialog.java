package com.nordan.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.google.android.material.textview.MaterialTextView;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NordanLoadingDialog {

    public static Dialog createLoadingDialog(Activity activity, String loadingText) {
        return createLoadingDialog(activity, loadingText, 0, -1);
    }

    public static Dialog createLoadingDialog(Activity activity, String loadingText, int accentColor) {
        return createLoadingDialog(activity, loadingText, accentColor, -1);
    }

    public static Dialog createLoadingDialog(Activity activity, String loadingText, int accentColor, int progress) {
        Dialog loadingDialog = new Dialog(activity);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Optional.ofNullable(loadingDialog.getWindow())
                .ifPresent(window -> window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)));
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.nordan_loading_dialog);
        ((MaterialTextView) loadingDialog.findViewById(R.id.loading_text)).setText(loadingText);
        ProgressBar loadingBar = loadingDialog.findViewById(R.id.progress_bar);
        if (accentColor > 0) {
            loadingBar.getIndeterminateDrawable().setColorFilter(activity.getColor(accentColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        if (progress > -1) {
            loadingBar.setIndeterminate(false);
            loadingBar.setProgress(progress);
        }
        return loadingDialog;
    }
}
