package co.wm21.https.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class DialogHelper {

    /**
     * Shows a reusable alert dialog.
     *
     * @param context  The context in which the dialog should be shown.
     * @param title    The title of the dialog.
     * @param message  The message to display in the dialog.
     * @param positiveButtonText  The text for the positive button.
     * @param negativeButtonText  The text for the negative button (optional, pass null to hide it).
     * @param positiveCallback    Callback for the positive button click (optional, pass null to ignore).
     * @param negativeCallback    Callback for the negative button click (optional, pass null to ignore).
     */
    public static void showAlertDialog(
            Context context,
            String title,
            String message,
            String positiveButtonText,
            String negativeButtonText,
            DialogInterface.OnClickListener positiveCallback,
            DialogInterface.OnClickListener negativeCallback
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        // Set positive button
        builder.setPositiveButton(positiveButtonText, (dialog, which) -> {
            if (positiveCallback != null) {
                positiveCallback.onClick(dialog, which);
            }
        });

        // Set negative button if text is provided
        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText, (dialog, which) -> {
                if (negativeCallback != null) {
                    negativeCallback.onClick(dialog, which);
                }
            });
        }

        // Create and show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
