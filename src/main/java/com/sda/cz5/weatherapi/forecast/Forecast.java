
package com.sda.cz5.weatherapi.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Forecast {


    @JsonProperty("list")
    private List<ForecastItem> forecastItem = null;

    //no need specify explicit JsonProperty, name is same as name in json
    private City city;



}
