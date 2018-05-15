package arkadiuszpalka.fiszki.ui.directories.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.ui.directories.DirectoriesMvp;

/**
 * Created by Arkadiusz Pałka on 26.04.2018.
 */

public class DirectoriesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final DirectoriesListPresenter presenter;
    private static final int VIEW_DIRECTORY = 0;
    private static final int VIEW_PROGRESS_BAR = 1;

    public DirectoriesRecyclerViewAdapter(DirectoriesListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getDirectory(position) == null ? VIEW_PROGRESS_BAR : VIEW_DIRECTORY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_DIRECTORY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_directory, parent, false);
            return new DirectoriesViewHolder(view);
        } else if (viewType == VIEW_PROGRESS_BAR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DirectoriesViewHolder) {
            presenter.onBindDirectoriesRowViewAtPosition(position, (DirectoriesViewHolder) holder);
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getDirectoriesRowsCount();
    }

    public class DirectoriesViewHolder extends RecyclerView.ViewHolder implements DirectoriesMvp.ItemView, View.OnClickListener, View.OnLongClickListener {

        private TextView name, creationDate;
        private View itemView;
        private ViewHolderOnItemClickListener recyclerOnItemClickListener;
        private ViewHolderOnLongClickItemListener recyclerOnLongItemClickListener;
        private int id;

        DirectoriesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.text_dir_item_title);
            creationDate = itemView.findViewById(R.id.text_dir_item_date);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void setRecyclerOnItemClickListener(ViewHolderOnItemClickListener recyclerOnItemClickListener) {
            this.recyclerOnItemClickListener = recyclerOnItemClickListener;
        }

        @Override
        public void setRecyclerOnLongItemClickListener(ViewHolderOnLongClickItemListener recyclerOnLongItemClickListener) {
            this.recyclerOnLongItemClickListener = recyclerOnLongItemClickListener;
        }

        @Override
        public void setDirectoryTitle(String title) {
            name.setText(title);
        }

        @Override
        public void setDirectoryDateOfCreated(String date) {
            creationDate.setText(date);
        }

        @Override
        public int getDirectoryId() {
            return id;
        }

        @Override
        public void setDirectoryId(int id) {
            this.id = id;
        }

        @Override
        public View getItemView() {
            return itemView;
        }

        @Override
        public void onClick(View view) {
            if (recyclerOnItemClickListener != null) {
                recyclerOnItemClickListener.onItemClickListener(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (recyclerOnLongItemClickListener != null) {
                recyclerOnLongItemClickListener.onLongItemClickListener(getAdapterPosition());
            }
            return true;
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder implements DirectoriesMvp.LoadingView {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_item_loading);
        }
    }
}
