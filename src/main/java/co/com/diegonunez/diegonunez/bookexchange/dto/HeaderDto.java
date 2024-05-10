package co.com.diegonunez.diegonunez.bookexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeaderDto implements Serializable {

    private String status;
    private Integer httpCode;
    private String message;

}
