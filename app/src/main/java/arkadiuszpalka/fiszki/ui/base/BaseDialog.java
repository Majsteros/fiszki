package arkadiuszpalka.fiszki.ui.base;

import android.app.DialogFragment;
import android.content.Context;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.ui.dialog.DialogOnNegativeButtonListener;
import arkadiuszpalka.fiszki.ui.dialog.DialogOnPositiveButtonListener;

/**
 * Created by Arkadiusz Pałka on 28.04.2018.
 */

public abstract class BaseDialog extends DialogFragment implements BaseMvp.DialogView {

    private BaseActivity activity;

    protected DialogOnPositiveButtonListener positiveButtonListener;
    protected DialogOnNegativeButtonListener negativeButtonListener;

    protected int positiveButtonTextId = R.string.dialog_ok;
    protected int negativeButtonTextId = R.string.dialog_cancel;

    public void setPositiveButtonText(int positiveButtonTextId) {
        this.positiveButtonTextId = positiveButtonTextId;
    }

    public void setNegativeButtonText(int negativeButtonTextId) {
        this.negativeButtonTextId = negativeButtonTextId;
    }

    public void setPositiveButtonListener(DialogOnPositiveButtonListener positiveButtonListener) {
        this.positiveButtonListener = positiveButtonListener;
    }

    public void setNegativeButtonListener(DialogOnNegativeButtonListener negativeButtonListener) {
        this.negativeButtonListener = negativeButtonListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.activity = (BaseActivity) context;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void dismissDialog(String tag) {
        dismiss();
        getBaseActivity().onFragmentDetached(tag);
    }

    @Override
    public void showMessage(String message) {
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public void onError(String message) {
        if (activity != null) {
            activity.onError(message);
        }
    }

    @Override
    public void onError(int resId) {
        if (activity != null) {
            activity.onError(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkConnected();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }
}
