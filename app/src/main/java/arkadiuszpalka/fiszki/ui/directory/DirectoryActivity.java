package arkadiuszpalka.fiszki.ui.directory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import arkadiuszpalka.fiszki.R;
import arkadiuszpalka.fiszki.data.AppDataManager;
import arkadiuszpalka.fiszki.ui.base.BaseActivity;
import arkadiuszpalka.fiszki.utils.AppConstants;

import static arkadiuszpalka.fiszki.utils.AppConstants.EXTRA_DIRECTORY_NAME;

public class DirectoryActivity extends BaseActivity implements DirectoryMvp.View{

    private DirectoryPresenter<DirectoryMvp.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Log.d("CARD", "not null");
        }

        Intent extra = getIntent();
        if (extra != null) {
            setTitle(extra.getStringExtra(EXTRA_DIRECTORY_NAME));
        }

        presenter = new DirectoryPresenter<>(AppDataManager.getInstance());
        presenter.onAttach(this);

    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
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
                    //add fiszka
                } else {
                    onError(R.string.error_no_connection_message);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DirectoryActivity.class);
    }


}
