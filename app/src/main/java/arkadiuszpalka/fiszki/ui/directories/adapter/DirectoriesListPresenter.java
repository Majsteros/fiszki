package arkadiuszpalka.fiszki.ui.directories.adapter;

import android.util.Log;

import java.util.ArrayList;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.data.DataManager;
import arkadiuszpalka.fiszki.data.network.ApiHandler;
import arkadiuszpalka.fiszki.data.network.model.Directory;
import arkadiuszpalka.fiszki.ui.directories.DirectoriesMvp;
import arkadiuszpalka.fiszki.ui.directories.DirectoriesPresenter;
import arkadiuszpalka.fiszki.utils.CommonUtils;

import static arkadiuszpalka.fiszki.utils.AppConstants.MAX_DOWNLOAD_COUNT;

/**
 * Created by Arkadiusz Pałka on 26.04.2018.
 */

public class DirectoriesListPresenter<V extends DirectoriesMvp.View> extends DirectoriesPresenter<V> implements DirectoriesMvp.Presenter.onLoadMoreListener {

    private static final String TAG = "DirectoriesListP";

    private ArrayList<Directory> directories;

    public DirectoriesListPresenter(DataManager dataManager, ArrayList<Directory> directories) {
        super(dataManager);
        this.directories = directories;
    }

    void onBindDirectoriesRowViewAtPosition(int position, DirectoriesMvp.ItemView itemView) {
        Directory directory = directories.get(position);
        itemView.setDirectoryTitle(directory.getName());
        itemView.setDirectoryDateOfCreated(CommonUtils.formatApiDate(directory.getDate()));
        itemView.setDirectoryId(directory.getId());
        itemView.setRecyclerOnItemClickListener(pos -> getActivityView().openDirectoryActivity(directory.getName()));
        itemView.setRecyclerOnLongItemClickListener(pos -> getActivityView().showPopupMenu(itemView.getItemView(), itemView.getDirectoryId(), position));
        Log.d(TAG, "Position: " + position + "\nIndex: " + directories.indexOf(directory) + "\nTitle: " + directory.getName() + "\ncounts: " + getDirectoriesRowsCount());
    }

    int getDirectoriesRowsCount() {
        return directories.size();
    }

    public Directory getDirectory(int position) {
        return directories.get(position);
    }

    public void removeDirectory(int index) {
        directories.remove(index);
    }

    public void addDirectory(Directory directory) {
        directories.add(directory);
    }

    public void setDirectoriesViewData(ArrayList<Directory> directories) {
        this.directories = directories;
    }

    @Override
    public void onLoadMore(int startRow) {
        if (!(getActivityView().isNetworkConnected())) {
            getActivityView().setLoading(false);
            getActivityView().showMessage(R.string.error_no_connection_refresh);
            return;
        }

        directories.add(null);
        getActivityView().notifyRecyclerViewDataInserted(getDirectoriesRowsCount() - 1);

        getDataManager().getDirectories(startRow, MAX_DOWNLOAD_COUNT,
                new ApiHandler.OnGetDirectoriesListener() {
                    @Override
                    public void onSuccess(ArrayList<Directory> newDirectories) {
                        if (isViewAttached()) {
                            directories.remove(getDirectoriesRowsCount() - 1);
                            getActivityView().notifyRecyclerViewDataRemoved(getDirectoriesRowsCount());

                            if (newDirectories != null) {
                                directories.addAll(startRow, newDirectories);
                            }

                            getActivityView().notifyRecyclerViewDataInserted(getDirectoriesRowsCount());
                            getActivityView().setLoading(false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        directories.remove(getDirectoriesRowsCount() - 1);
                        if (isViewAttached()) {
                            getActivityView().notifyRecyclerViewDataRemoved(getDirectoriesRowsCount());
                            getActivityView().setLoading(false);
                            getActivityView().showMessage(R.string.error_unknown_occurred);
                        }
                    }
                });
    }
}
