package com.nordan.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
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
        private int icon;
        private NordanAlertDialogListener positiveListener;
        private NordanAlertDialogListener negativeListener;
        private int backgroundColor;
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

        public Builder setBackgroundColor(int bgColor) {
            this.backgroundColor = bgColor;
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

        public Builder setIcon(int icon) {
            this.icon = icon;
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
            if (positiveBtnText != null) {
                positiveButton.setText(positiveBtnText);
            }
            if (negativeBtnText != null) {
                negativeButton.setText(negativeBtnText);
            } else {
                negativeButton.setVisibility(View.GONE);
            }
            if (positiveListener != null) {
                positiveButton.setOnClickListener(click -> {
                    positiveListener.onClick();
                    dialog.dismiss();
                });
            } else {
                positiveButton.setOnClickListener(click -> dialog.dismiss());
            }
            if (negativeListener != null) {
                negativeButton.setVisibility(View.VISIBLE);
                negativeButton.setOnClickListener(view1 -> {
                    negativeListener.onClick();
                    dialog.dismiss();
                });
            } else {
                negativeButton.setOnClickListener(click -> dialog.dismiss());
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
            if (icon == 0 && backgroundColor == 0) {
                dialog.findViewById(R.id.relative_header).setVisibility(View.GONE);
                return;
            }
            if (icon != 0) {
                iconImg.setImageResource(icon);
                iconImg.setVisibility(View.VISIBLE);
            }
            if (backgroundColor != 0) {
                view.setBackgroundColor(backgroundColor);
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
