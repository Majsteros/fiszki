package arkadiuszpalka.fiszki.ui.base;

/**
 * Created by Arkadiusz Pałka on 26.04.2018.
 */

public interface BaseMvp {

    interface View {
        void showMessage(String message);

        void showMessage(int resId);

        void onError(String message);

        void onError(int resId);

        boolean isNetworkConnected();

    }

    interface Presenter<V extends View> {

        void onAttach(V view);

        void onDetach();

    }

    interface DialogView extends View {

        void dismissDialog(String tag);
    }

    interface FragmentCallback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);

    }

}
