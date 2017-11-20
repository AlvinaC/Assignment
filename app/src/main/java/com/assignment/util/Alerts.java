package com.assignment.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assignment.R;


/**
 * Summary: Alerts at one place
 */
public class Alerts {

    private static Alerts ourInstance;

    public static Alerts getInstance() {
        if (ourInstance == null)
            ourInstance = new Alerts();
        return ourInstance;
    }

    private Alerts() {
    }

    //show progressdialog in the app
    public void showDialog(final Context context, final ProgressDialog dialog, String message) {
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface mdialog) {
                ProgressBar v = (ProgressBar) dialog.findViewById(android.R.id.progress);
                //PorterDuff.Mode.SRC_IN,  equates to drawing the source image/color inside the target image
                if (Build.VERSION.SDK_INT >= 21) {
                    v.setProgressTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.intro_page_indicator_dark)));
                } else
                    v.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.intro_page_indicator_dark),
                            PorterDuff.Mode.SRC_IN);

            }
        });
        if (context != null)
            if (dialog != null) {
                dialog.show();
            }
    }

    //dismiss progress dialog for the app
    public void dismiss(Context context, ProgressDialog dialog) {
        if (context != null)
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

    }


    //will be used when any request returns with an error
    public void showDialogOnResponseError(final Context context) {
        showDialogOK(context, Constants.INTERNET_ALERT_TEXT, setUpDialogListener(context));
    }

    //will be used when any request returns with an error
    private DialogInterface.OnClickListener setUpDialogListener(final Context context) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if (context != null)
                            if (dialog != null)
                                dialog.dismiss();
                        break;
                }
            }
        };
        return listener;
    }


    public static void showDialogOK(final Context context, String message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setTextColor(context.getResources().getColor(R.color.intro_page_indicator_dark));
                LinearLayout parent = (LinearLayout) positiveButton.getParent();
                parent.setGravity(Gravity.CENTER_HORIZONTAL);
                View leftSpacer = parent.getChildAt(1);
                leftSpacer.setVisibility(View.GONE);
                TextView messageText = (TextView) ((AlertDialog) dialog).findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);
            }

        });
        dialog.show();
    }


}
