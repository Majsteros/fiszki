package arkadiuszpalka.fiszki.ui.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.utils.NetworkUtils;

/**
 * Created by Arkadiusz Pałka on 26.04.2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseMvp.View, BaseMvp.FragmentCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_unknown_occurred, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void onError(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(BaseActivity.this);
        dialog.setTitle(getString(R.string.default_title_error));
        dialog.setMessage(message);
        dialog.setNeutralButton(R.string.dialog_ok, null);
        dialog.create().show();
    }

    @Override
    public void onError(int resId) {
        onError(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }
}
