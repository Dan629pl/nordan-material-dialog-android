package com.nordan.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.droidsonroids.gif.GifImageView;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NordanAlertDialog {

    public static class Builder {

        private String title;
        private String message;
        private String positiveBtnText;
        private String negativeBtnText;
        private Activity activity;
        private Animation animation;
        private int headerIconResource;
        private int dialogAccentColor;
        private Bitmap headerIconDrawable;
        private boolean isGif;
        private NordanAlertDialogListener positiveListener;
        private NordanAlertDialogListener negativeListener;
        private int headerColor;
        private boolean isCancelable;
        private DialogType dialogType;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDialogType(DialogType dialogType) {
            this.dialogType = dialogType;
            return this;
        }

        public Builder setDialogAccentColor(int color) {
            this.dialogAccentColor = color;
            return this;
        }

        public Builder setHeaderColor(int bgColor) {
            this.headerColor = bgColor;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveBtnText(String positiveBtnText) {
            this.positiveBtnText = positiveBtnText;
            return this;
        }

        public Builder setNegativeBtnText(String negativeBtnText) {
            this.negativeBtnText = negativeBtnText;
            return this;
        }

        public Builder setIcon(int icon, boolean isGif) {
            this.headerIconResource = icon;
            this.isGif = isGif;
            return this;
        }

        public Builder setIcon(Bitmap icon, boolean isGif) {
            this.headerIconDrawable = icon;
            this.isGif = isGif;
            return this;
        }

        public Builder setAnimation(Animation animation) {
            this.animation = animation;
            return this;
        }

        public Builder onPositiveClicked(NordanAlertDialogListener positiveListener) {
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder onNegativeClicked(NordanAlertDialogListener negativeListener) {
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder isCancellable(boolean cancel) {
            this.isCancelable = cancel;
            return this;
        }

        public Dialog build() {
            final MaterialTextView messageDialog;
            final MaterialTextView titleDialog;
            final GifImageView iconImg;
            final MaterialButton negativeButton;
            final MaterialButton positiveButton;
            final View view;
            final Dialog dialog;

            dialog = getAnimationDialog();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Optional.ofNullable(dialog.getWindow()).ifPresent(window -> window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)));
            dialog.setCancelable(isCancelable);
            dialog.setContentView(R.layout.nordan_alert_dialog);
            view = dialog.findViewById(R.id.background);
            titleDialog = dialog.findViewById(R.id.title);
            messageDialog = dialog.findViewById(R.id.message);
            iconImg = dialog.findViewById(R.id.icon);
            negativeButton = dialog.findViewById(R.id.negativeBtn);
            positiveButton = dialog.findViewById(R.id.positiveBtn);
            titleDialog.setText(title);
            messageDialog.setText(message);
            if (dialogType != null) {
                switch (dialogType) {
                    case ERROR:
                        setErrorDialog(iconImg, view);
                        break;
                    case WARNING:
                        setWarningDialog(iconImg, view);
                        break;
                    case QUESTION:
                        setQuestionDialog(iconImg, view);
                        break;
                    case INFORMATION:
                        setInformationDialog(iconImg, view);
                        break;
                    case SUCCESS:
                        setLevelCompleteDialog(iconImg, view);
                        break;
                    default:
                        break;
                }
            } else {
                setCustomDialog(dialog, iconImg, view);
            }
            if (positiveBtnText != null && !positiveBtnText.isEmpty()) {
                positiveButton.setText(positiveBtnText);
            }
            if (negativeBtnText != null && !negativeBtnText.isEmpty()) {
                negativeButton.setText(negativeBtnText);
            } else {
                negativeButton.setVisibility(View.GONE);
            }
            if (positiveListener != null && positiveBtnText != null && !positiveBtnText.isEmpty()) {
                positiveButton.setOnClickListener(click -> {
                    positiveListener.onClick();
                    dialog.dismiss();
                });
            } else {
                positiveButton.setOnClickListener(click -> dialog.dismiss());
            }
            if (negativeListener != null && negativeBtnText != null && !negativeBtnText.isEmpty()) {
                negativeButton.setVisibility(View.VISIBLE);
                negativeButton.setOnClickListener(view1 -> {
                    negativeListener.onClick();
                    dialog.dismiss();
                });
            } else {
                negativeButton.setOnClickListener(click -> dialog.dismiss());
            }
            if (dialogAccentColor > 0) {
                positiveButton.setBackgroundColor(activity.getColor(dialogAccentColor));
                negativeButton.setTextColor(activity.getColor(dialogAccentColor));
            }
            return dialog;
        }

        private void setLevelCompleteDialog(GifImageView iconImg, View view) {
            iconImg.setImageResource(R.drawable.ic_baseline_check_circle_24);
            iconImg.setVisibility(View.VISIBLE);
            view.setBackgroundColor(activity.getColor(R.color.colorGreen));
        }

        private void setWarningDialog(GifImageView iconImg, View view) {
            iconImg.setImageResource(R.drawable.ic_baseline_warning_24);
            iconImg.setVisibility(View.VISIBLE);
            view.setBackgroundColor(activity.getColor(R.color.colorYellow));
        }

        private void setErrorDialog(GifImageView iconImg, View view) {
            iconImg.setImageResource(R.drawable.ic_baseline_error_24);
            iconImg.setVisibility(View.VISIBLE);
            view.setBackgroundColor(activity.getColor(R.color.colorRed));
        }

        private void setQuestionDialog(GifImageView iconImg, View view) {
            iconImg.setImageResource(R.drawable.ic_baseline_help_24);
            iconImg.setVisibility(View.VISIBLE);
            view.setBackgroundColor(activity.getColor(R.color.colorBlue));
        }

        private void setInformationDialog(GifImageView iconImg, View view) {
            iconImg.setImageResource(R.drawable.ic_baseline_info_24);
            iconImg.setVisibility(View.VISIBLE);
            view.setBackgroundColor(activity.getColor(R.color.colorPurple));
        }

        private void setCustomDialog(Dialog dialog, GifImageView iconImg, View view) {
            RelativeLayout relativeHeader = dialog.findViewById(R.id.relative_header);
            if (headerIconResource == 0 && headerColor == 0 && headerIconDrawable == null) {
                relativeHeader.setVisibility(View.GONE);
                return;
            }
            if (headerIconResource != 0) {
                iconImg.setImageResource(headerIconResource);
                iconImg.setVisibility(View.VISIBLE);
                if (isGif) {
                    relativeHeader.getLayoutParams().height = 250;
                    iconImg.setScaleType(ScaleType.CENTER_CROP);
                }
            }
            if (headerColor != 0) {
                view.setBackgroundColor(headerColor);
            }
        }

        private Dialog getAnimationDialog() {
            Dialog dialog;
            if (animation == null) {
                dialog = new Dialog(activity);
            } else {
                switch (animation) {
                    case POP:
                        dialog = new Dialog(activity, R.style.NordanDialogPopTheme);
                        break;
                    case SIDE:
                        dialog = new Dialog(activity, R.style.NordanDialogSideTheme);
                        break;
                    case SLIDE:
                        dialog = new Dialog(activity, R.style.NordanDialogSlideTheme);
                        break;
                    default:
                        dialog = new Dialog(activity);
                        break;
                }
            }
            return dialog;
        }
    }
}
