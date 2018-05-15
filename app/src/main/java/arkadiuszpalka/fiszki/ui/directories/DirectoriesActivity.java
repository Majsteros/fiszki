package arkadiuszpalka.fiszki.ui.directories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.data.AppDataManager;
import arkadiuszpalka.fiszki.data.network.model.Directory;
import arkadiuszpalka.fiszki.ui.base.BaseActivity;
import arkadiuszpalka.fiszki.ui.dialog.ConfirmDialog;
import arkadiuszpalka.fiszki.ui.dialog.InputDirectoryDialog;
import arkadiuszpalka.fiszki.ui.directories.adapter.DirectoriesListPresenter;
import arkadiuszpalka.fiszki.ui.directories.adapter.DirectoriesRecyclerViewAdapter;
import arkadiuszpalka.fiszki.ui.directory.DirectoryActivity;
import arkadiuszpalka.fiszki.utils.AppConstants;

import static arkadiuszpalka.fiszki.utils.AppConstants.TAG_CONFIRM_BOX;
import static arkadiuszpalka.fiszki.utils.AppConstants.TAG_DIRECTORY_ADD;
import static arkadiuszpalka.fiszki.utils.AppConstants.TAG_DIRECTORY_RENAME;

public class DirectoriesActivity extends BaseActivity implements DirectoriesMvp.View {

    private static final String TAG = "DirectoriesActivity";

    private TextView noConnectionMessage;

    private DirectoriesPresenter<DirectoriesMvp.View> presenter;
    private DirectoriesListPresenter<DirectoriesActivity> listPresenter;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter;

    private int currentItems, itemCount, scrollOutItems;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directories);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        noConnectionMessage = findViewById(R.id.text_no_connection);
        setSupportActionBar(toolbar);

        AppDataManager appDataManager = AppDataManager.getInstance();

        presenter = new DirectoriesPresenter<>(appDataManager);
        presenter.onAttach(this);

        listPresenter = new DirectoriesListPresenter<>(appDataManager, new ArrayList<>());
        listPresenter.onAttach(this);

        // Adds the loading bar to recycler view
        listPresenter.addDirectory(null);
        presenter.getDirectories(0);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_directories);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerRecyclerView = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerRecyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollOutItems = layoutManagerRecyclerView.findFirstVisibleItemPosition();
                currentItems = layoutManagerRecyclerView.getChildCount();
                itemCount = layoutManagerRecyclerView.getItemCount();

                if (!isLoading && (currentItems + scrollOutItems == itemCount) && scrollOutItems > 0) {
                    isLoading = true;
                    recyclerView.post(() -> listPresenter.onLoadMore(itemCount));
                }
            }
        });

        recyclerViewAdapter = new DirectoriesRecyclerViewAdapter(listPresenter);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        //refresh - download directory and insert it
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        listPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showPopupMenu(View view, int directoryId, int position) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_popup_directory, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popup_directory_rename:
                    InputDirectoryDialog renameDialog = new InputDirectoryDialog();
                    renameDialog.setPositiveButtonText(R.string.dialog_rename);
                    renameDialog.setPositiveButtonListener(() -> presenter.renameDirectory(directoryId, renameDialog.getInputText(), position));
                    renameDialog.show(getFragmentManager(), TAG_DIRECTORY_RENAME);
                    return true;
                case R.id.action_popup_directory_delete:
                    ConfirmDialog confirmDialog = new ConfirmDialog();
                    confirmDialog.setTitle(getString(R.string.dialog_delete_question));
                    confirmDialog.setMessage(getString(R.string.dialog_delete_message));
                    confirmDialog.setPositiveButtonText(R.string.dialog_delete);
                    confirmDialog.setPositiveButtonListener(() -> presenter.removeDirectory(directoryId, position));
                    confirmDialog.show(getFragmentManager(), TAG_CONFIRM_BOX);
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (isNetworkConnected()) {
                    InputDirectoryDialog dialog = new InputDirectoryDialog();
                    dialog.setPositiveButtonText(R.string.dialog_rename);
                    dialog.setPositiveButtonListener(() -> presenter.doAddDirectory(dialog.getInputText()));
                    dialog.show(getFragmentManager(), TAG_DIRECTORY_ADD);
                } else {
                    onError(R.string.error_no_connection_message);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void notifyRecyclerViewDataChanged() {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyRecyclerViewDataInserted(int position) {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyItemInserted(position);
        }
    }

    @Override
    public void notifyRecyclerViewDataRemoved(int position) {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void notifyRecyclerViewRangeChanged(int start, int count) {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyItemRangeChanged(start, count);
        }
    }

    @Override
    public void notifyRecyclerViewItemChanged(int position) {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void setRecyclerViewData(ArrayList<Directory> directories) {
        if (listPresenter != null) {
            listPresenter.setDirectoriesViewData(directories);
        }
    }

    @Override
    public int getRecyclerViewItemCount() {
        if (recyclerViewAdapter != null) {
            return recyclerViewAdapter.getItemCount();
        }
        return 0;
    }

    @Override
    public void removeRecyclerViewItem(int index) {
        if (listPresenter != null) {
            listPresenter.removeDirectory(index);
        }
    }

    @Override
    public Directory getRecyclerViewItem(int index) {
        if (listPresenter != null) {
            return listPresenter.getDirectory(index);
        }
        return null;
    }

    @Override
    public void openDirectoryActivity(String extraName) {
        Intent intent = DirectoryActivity.getStartIntent(this);
        intent.putExtra(AppConstants.EXTRA_DIRECTORY_NAME, extraName);
        startActivity(intent);
    }

    @Override
    public void showNoConnectionMessage() {
        noConnectionMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConnectionMessage() {
        noConnectionMessage.setVisibility(View.GONE);
    }
}
