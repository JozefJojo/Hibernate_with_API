
package com.sda.cz5.weatherapi.forecast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ForecastItem {


    @JsonProperty("main")
    private BasicWeatherAttributes basicWeatherAttributes;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("dt_txt")
    private String dtTxt;

    @JsonProperty("main")
    public BasicWeatherAttributes getMain() {
        return basicWeatherAttributes;
    }

    @JsonProperty("main")
    public void setMain(BasicWeatherAttributes basicWeatherAttributes) {
        this.basicWeatherAttributes = basicWeatherAttributes;
    }

    @JsonProperty("wind")
    public Wind getWind() {
        return wind;
    }

    @JsonProperty("wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @JsonProperty("dt_txt")
    public String getDtTxt() {
        return dtTxt;
    }

    @JsonProperty("dt_txt")
    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }


}
