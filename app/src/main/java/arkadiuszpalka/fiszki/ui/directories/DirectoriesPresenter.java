package arkadiuszpalka.fiszki.ui.directories;

import java.util.ArrayList;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.data.DataManager;
import arkadiuszpalka.fiszki.data.network.ApiHandler;
import arkadiuszpalka.fiszki.data.network.model.ApiResponse;
import arkadiuszpalka.fiszki.data.network.model.Directory;
import arkadiuszpalka.fiszki.ui.base.BasePresenter;

import static arkadiuszpalka.fiszki.utils.AppConstants.MAX_DOWNLOAD_COUNT;

/**
 * Created by Arkadiusz Pałka on 26.04.2018.
 */

public class DirectoriesPresenter<V extends DirectoriesMvp.View> extends BasePresenter<V> implements DirectoriesMvp.Presenter<V> {

    private static final String TAG = "DirectoriesPresenter";

    public DirectoriesPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getDirectories() {
        getDirectories(0);
    }

    @Override
    public void getDirectories(int startRow) {
        if (isViewAttached() && !(getActivityView().isNetworkConnected())) {
            getActivityView().showNoConnectionMessage();
            // Removes loading bar
            getActivityView().removeRecyclerViewItem(startRow);
            getActivityView().notifyRecyclerViewDataRemoved(startRow - 1);
            return;
        }

        getDataManager().getDirectories(startRow, MAX_DOWNLOAD_COUNT, new ApiHandler.OnGetDirectoriesListener() {
            @Override
            public void onSuccess(ArrayList<Directory> directories) {
                if (directories != null) {

                    if (isViewAttached()) {
                        getActivityView().setRecyclerViewData(directories);
                        getActivityView().notifyRecyclerViewDataChanged();
                    }

                } else if (isViewAttached()) {
                    getActivityView().onError(R.string.error_unknown_occurred);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (isViewAttached()) {
                    getActivityView().onError(R.string.error_unknown_occurred);
                }
            }
        });
    }

    @Override
    public void doAddDirectory(String name) {
        if (!(getActivityView().isNetworkConnected())) {
            getActivityView().onError(R.string.error_no_connection_message);
            return;
        }

        getDataManager().doAddDirectory(name, new ApiHandler.OnActionDirectoryListener() {
            @Override
            public void onSuccess(String result) {
                if (isViewAttached()) {

                    if (result == null) {
                        getActivityView().onError(R.string.error_unknown_occurred);
                    } else if (result.equals("false")) {
                        getActivityView().showMessage(R.string.error_directory_add);
                    } else {
                        getActivityView().showMessage(R.string.success_directory_add);
                    }

                    getActivityView().notifyRecyclerViewDataChanged();
                    getActivityView().openDirectoryActivity(name);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (isViewAttached()) {
                    getActivityView().onError(R.string.error_unknown_occurred);
                }
            }
        });
    }

    @Override
    public void removeDirectory(int id, int position) {
        if (!(getActivityView().isNetworkConnected())) {
            getActivityView().onError(R.string.error_no_connection_message);
            return;
        }

        getDataManager().removeDirectory(id, new ApiHandler.OnActionDirectoryListener() {
            @Override
            public void onSuccess(String result) {
                if (isViewAttached()) {
                    getActivityView().notifyRecyclerViewDataRemoved(position);
                    getActivityView().notifyRecyclerViewRangeChanged(position, getActivityView().getRecyclerViewItemCount());
                    getActivityView().removeRecyclerViewItem(position);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (isViewAttached()) {
                    getActivityView().onError(R.string.error_unknown_occurred);
                }
            }
        });
    }

    @Override
    public void renameDirectory(int id, String name, int position) {
        if (!(getActivityView().isNetworkConnected())) {
            getActivityView().onError(R.string.error_no_connection_message);
            return;
        }

        getDataManager().renameDirectory(id, name, new ApiHandler.OnResponseListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                if (isViewAttached()) {
                    getActivityView().showMessage(R.string.success_directory_rename);
                    Directory directory = getActivityView().getRecyclerViewItem(position);
                    directory.setName(name);
                    getActivityView().notifyRecyclerViewItemChanged(position);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (isViewAttached()) {
                    getActivityView().onError(R.string.error_unknown_occurred);
                }
            }
        });
    }
}
