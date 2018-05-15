package arkadiuszpalka.fiszki.ui.directory;

import arkadiuszpalka.fiszki.ui.base.BaseMvp;

/**
 * Created by Arkadiusz Pałka on 29.04.2018.
 */

public interface DirectoryMvp {

    interface View extends BaseMvp.View {
    }

    interface Presenter<V extends DirectoryMvp.View> extends BaseMvp.Presenter<V> {
    }

}
