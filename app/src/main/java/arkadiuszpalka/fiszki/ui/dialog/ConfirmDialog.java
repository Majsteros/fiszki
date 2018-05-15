package arkadiuszpalka.fiszki.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.ui.base.BaseDialog;

/**
 * Created by Arkadiusz Pałka on 30.04.2018.
 */

public class ConfirmDialog extends BaseDialog {

    private CharSequence title, message;

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public void setMessage(CharSequence message) {
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_confirm_box, null);

        TextView titleText = view.findViewById(R.id.dialog_confirm_title);
        TextView messageText = view.findViewById(R.id.dialog_confirm_message);

        titleText.setText(title);
        messageText.setText(message);

        builder.setView(view)
                .setPositiveButton(positiveButtonTextId, ((dialogInterface, i) -> {
                    if (positiveButtonListener != null) {
                        positiveButtonListener.onPositiveButtonClick();
                    }
                }))
                .setNegativeButton(negativeButtonTextId, ((dialogInterface, i) -> {
                    if (negativeButtonListener != null) {
                        negativeButtonListener.onNegativeButtonClick();
                    }
                }));
        setCancelable(false);

        return builder.create();
    }
}
