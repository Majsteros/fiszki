package arkadiuszpalka.fiszki.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arkadiusz Pałka on 07.05.2018.
 */
public class ApiResponse {

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("headers")
    @Expose
    private List<Object> headers = null;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Object> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Object> headers) {
        this.headers = headers;
    }

}
