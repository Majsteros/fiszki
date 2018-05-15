package arkadiuszpalka.fiszki.ui.base;

import arkadiuszpalka.fiszki.data.DataManager;

/**
 * Created by Arkadiusz Pałka on 26.04.2018.
 */

public class BasePresenter<V extends BaseMvp.View> implements BaseMvp.Presenter<V> {

    private final DataManager dataManager;
    private V activityView;

    public BasePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    protected DataManager getDataManager() {
        return dataManager;
    }

    protected V getActivityView() {
        return activityView;
    }

    protected boolean isViewAttached() {
        return activityView != null;
    }

    @Override
    public void onAttach(V view) {
        this.activityView = view;
    }

    @Override
    public void onDetach() {
        this.activityView = null;
    }
}
