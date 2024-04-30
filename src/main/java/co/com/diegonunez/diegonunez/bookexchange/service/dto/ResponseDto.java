package co.com.diegonunez.diegonunez.bookexchange.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto implements Serializable {

    @JsonProperty("HeaderResponse")
    private HeaderDto headerDto;
    @JsonProperty("BodyResponse")
    private BodyResponseDto bodyResponseDto;


}
