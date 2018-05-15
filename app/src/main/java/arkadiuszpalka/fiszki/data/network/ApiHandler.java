package arkadiuszpalka.fiszki.data.network;

import java.util.ArrayList;

import arkadiuszpalka.fiszki.data.network.model.ApiResponse;
import arkadiuszpalka.fiszki.data.network.model.Directory;

public interface ApiHandler {

    interface OnGetDirectoriesListener {

        void onSuccess(ArrayList<Directory> directories);

        void onFailure(Throwable t);
    }

    interface OnActionDirectoryListener {

        void onSuccess(String result);

        void onFailure(Throwable t);
    }

    interface OnResponseListener {

        void onSuccess(ApiResponse response);

        void onFailure(Throwable t);
    }

    void doAddDirectory(String name, OnActionDirectoryListener listener);

    void removeDirectory(int id, OnActionDirectoryListener listener);

    void renameDirectory(int id, String name, OnResponseListener listener);

    void getDirectories(OnGetDirectoriesListener listener);

    void getDirectories(int startRow, int rowsCount, OnGetDirectoriesListener listener);

}
