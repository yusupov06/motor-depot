package uz.motordepot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorData {

    private String msg;

    private Integer code;

    private String fieldName;

    public ErrorData(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
