package arkadiuszpalka.fiszki.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arkadiusz Pałka on 28.04.2018.
 */

public class Directories {

    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("catalogs")
    @Expose
    private List<Directory> directories = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

}
