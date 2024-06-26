package co.com.diegonunez.diegonunez.bookexchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto implements Serializable {

    @JsonProperty("HeaderResponse")
    private HeaderDto headerDto;
    @JsonProperty("BodyResponse")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BodyResponseDto bodyResponseDto;


}
