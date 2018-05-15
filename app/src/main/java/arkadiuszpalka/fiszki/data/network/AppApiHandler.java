package arkadiuszpalka.fiszki.data.network;

import java.util.ArrayList;

import arkadiuszpalka.fiszki.data.network.model.ApiResponse;
import arkadiuszpalka.fiszki.data.network.model.Directories;
import arkadiuszpalka.fiszki.data.network.model.Directory;
import arkadiuszpalka.fiszki.utils.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApiHandler implements ApiHandler {

    private static final String TAG = "AppApiHandler";

    private static volatile AppApiHandler instance;
    private static Retrofit retrofit = null;

    private AppApiHandler() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get single instance of this class.");
        }
    }

    public static AppApiHandler getInstance() {
        if (instance == null) {
            synchronized (AppApiHandler.class) {
                if (instance == null) instance = new AppApiHandler();
            }
        }
        return instance;
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @Override
    public void doAddDirectory(String name, OnActionDirectoryListener listener) {
        Call<String> callback = getRetrofit().create(ApiService.class).addDirectory(name);

        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void removeDirectory(int id, OnActionDirectoryListener listener) {
        Call<String> callback = getRetrofit().create(ApiService.class).removeDirectory(id);

        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void renameDirectory(int id, String name, OnResponseListener listener) {
        Call<ApiResponse> callback = getRetrofit().create(ApiService.class).renameDirectory(id, name);

        callback.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getDirectories(OnGetDirectoriesListener listener) {
        Call<Directories> callback = getRetrofit().create(ApiService.class).getDirectoriesList();

        ArrayList<Directory> directoryArrayList = new ArrayList<>();

        callback.enqueue(new Callback<Directories>() {
            @Override
            public void onResponse(Call<Directories> call, Response<Directories> response) {
                directoryArrayList.addAll(response.body().getDirectories());
                listener.onSuccess(directoryArrayList);
            }

            @Override
            public void onFailure(Call<Directories> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getDirectories(int startRow, int rowsCount, OnGetDirectoriesListener listener) {
        Call<Directories> callback = getRetrofit().create(ApiService.class).getDirectoriesList(startRow, rowsCount);

        ArrayList<Directory> directoryArrayList = new ArrayList<>();

        callback.enqueue(new Callback<Directories>() {
            @Override
            public void onResponse(Call<Directories> call, Response<Directories> response) {
                directoryArrayList.addAll(response.body().getDirectories());
                listener.onSuccess(directoryArrayList);
            }

            @Override
            public void onFailure(Call<Directories> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
