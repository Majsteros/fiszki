package arkadiuszpalka.fiszki.ui.directory;

import arkadiuszpalka.fiszki.data.AppDataManager;
import arkadiuszpalka.fiszki.data.DataManager;
import arkadiuszpalka.fiszki.ui.base.BasePresenter;

/**
 * Created by Arkadiusz Pałka on 29.04.2018.
 */

public class DirectoryPresenter<V extends DirectoryMvp.View> extends BasePresenter<V> implements DirectoryMvp.Presenter<V> {

    public DirectoryPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
