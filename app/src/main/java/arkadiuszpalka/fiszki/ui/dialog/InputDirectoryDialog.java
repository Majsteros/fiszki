package arkadiuszpalka.fiszki.ui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.ui.base.BaseDialog;

import static arkadiuszpalka.fiszki.utils.AppConstants.MAX_LENGTH;

/**
 * Created by Arkadiusz Pałka on 28.04.2018.
 */

public class InputDirectoryDialog extends BaseDialog {

    private EditText input;
    private TextView counter;

    private int textLength;

    public int getTextLength() {
        return textLength;
    }

    public String getInputText() {
        return input.getText().toString();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_directory,null);
        input = view.findViewById(R.id.input_directory_name);
        counter = view.findViewById(R.id.text_characters_counter);

        int defaultColor = counter.getCurrentTextColor();

        input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textLength = input.getText().length();
                counter.setText(String.format(Locale.getDefault(), "%d/" + MAX_LENGTH, textLength));
                if (textLength == Integer.parseInt(MAX_LENGTH)) {
                    counter.setTextColor(Color.RED);
                } else {
                    counter.setTextColor(defaultColor);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        builder.setView(view)
                .setPositiveButton(positiveButtonTextId, (dialogInterface, i) -> {
                    if (positiveButtonListener != null) {
                        positiveButtonListener.onPositiveButtonClick();
                    }
                    /*
                    if (getBaseActivity().isNetworkConnected()) {
                        if (input.getText().length() == 0) {
                            getBaseActivity().showMessage(R.string.error_null_string);
                        } else if (getBaseActivity() instanceof DirectoriesActivity) {
                            DirectoriesActivity activity = (DirectoriesActivity) getBaseActivity();
                            activity.getPresenter().doAddDirectory(String.valueOf(input.getText()));
                        }
                    } else {
                        getBaseActivity().onError(R.string.error_no_connection_message);
                    }
                    */
                })
                .setNegativeButton(negativeButtonTextId, (dialogInterface, i) -> {
                    if (negativeButtonListener != null) {
                        negativeButtonListener.onNegativeButtonClick();
                    }
                });
        setCancelable(false);

        return builder.create();
    }
}
