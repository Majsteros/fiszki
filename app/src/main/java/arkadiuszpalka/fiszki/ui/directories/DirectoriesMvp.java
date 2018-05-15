package arkadiuszpalka.fiszki.ui.directories;

import java.util.ArrayList;

import arkadiuszpalka.fiszki.data.network.model.Directory;
import arkadiuszpalka.fiszki.ui.base.BaseMvp;
import arkadiuszpalka.fiszki.ui.directories.adapter.ViewHolderOnItemClickListener;
import arkadiuszpalka.fiszki.ui.directories.adapter.ViewHolderOnLongClickItemListener;

/**
 * Created by Arkadiusz Pałka on 26.04.2018.
 */

public interface DirectoriesMvp {

    interface View extends BaseMvp.View {

        void showPopupMenu(android.view.View view, int directoryId, int position);

        void openDirectoryActivity(String extraName);

        void setLoading(boolean loading);

        void notifyRecyclerViewDataChanged();

        void notifyRecyclerViewDataInserted(int position);

        void notifyRecyclerViewDataRemoved(int position);

        void notifyRecyclerViewRangeChanged(int start, int count);

        void notifyRecyclerViewItemChanged(int position);

        void setRecyclerViewData(ArrayList<Directory> directories);

        int getRecyclerViewItemCount();

        void removeRecyclerViewItem(int index);

        Directory getRecyclerViewItem(int index);

        void showNoConnectionMessage();

        void hideNoConnectionMessage();
    }

    interface Presenter<V extends DirectoriesMvp.View> extends BaseMvp.Presenter<V> {

        void getDirectories();

        void getDirectories(int startRow);

        void doAddDirectory(String name);

        void removeDirectory(int id, int position);

        void renameDirectory(int id, String name, int position);

        interface onLoadMoreListener {

            void onLoadMore(int startRow);
        }
    }

    interface ItemView {

        void setDirectoryTitle(String title);

        void setDirectoryDateOfCreated(String date);

        int getDirectoryId();

        void setDirectoryId(int id);

        android.view.View getItemView();

        void setRecyclerOnItemClickListener(ViewHolderOnItemClickListener recyclerOnItemClickListener);

        void setRecyclerOnLongItemClickListener(ViewHolderOnLongClickItemListener recyclerOnItemClickListener);
    }

    interface LoadingView {

    }

}
