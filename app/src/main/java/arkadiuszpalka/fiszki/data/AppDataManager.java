package arkadiuszpalka.fiszki.data;

import arkadiuszpalka.fiszki.data.network.ApiHandler;
import arkadiuszpalka.fiszki.data.network.AppApiHandler;

public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private static volatile AppDataManager instance;
    private final ApiHandler apiHandler;

    private AppDataManager() {
        this.apiHandler = AppApiHandler.getInstance();
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get single instance of this class.");
        }
    }

    public static AppDataManager getInstance() {
        if (instance == null) {
            synchronized (AppDataManager.class) {
                if (instance == null) instance = new AppDataManager();
            }
        }
        return instance;
    }

    private ApiHandler getApiHandler() {
        return apiHandler;
    }

    @Override
    public void doAddDirectory(String name, OnActionDirectoryListener listener) {
        getApiHandler().doAddDirectory(name, listener);
    }

    @Override
    public void removeDirectory(int id, OnActionDirectoryListener listener) {
        getApiHandler().removeDirectory(id, listener);
    }

    @Override
    public void renameDirectory(int id, String name, OnResponseListener listener) {
        getApiHandler().renameDirectory(id, name, listener);
    }

    @Override
    public void getDirectories(OnGetDirectoriesListener listener) {
        getApiHandler().getDirectories(listener);
    }

    @Override
    public void getDirectories(int startRow, int rowsCount, OnGetDirectoriesListener listener) {
        getApiHandler().getDirectories(startRow, rowsCount, listener);
    }
}
