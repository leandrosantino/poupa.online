package poupa.online.api.responses;

import lombok.Data;

@Data
public class HttpResponse<T> {
    private String message;
    private T data;
}
