package com.sda.cz5.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CityForecast extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column
    private LocalDateTime dateTime;
    @Column
    private Integer pressure;
    @Column
    private Integer humidity;
    @Column
    private Double temp;
    @Column
    private Double windSpeed;

    @Enumerated(EnumType.STRING)
    private WindDirection windDirection;

    public double getTempCelsius(){
        return temp-273.15;
    }
    public enum WindDirection{
        N,
        S,
        W,
        E;

        public static WindDirection fromDegerees(int degree){
            if(degree>=45 && degree <=135){
                return N;
            }
            if(degree>135 && degree <=225){
                return W;
            }
            if(degree>225 && degree <=315){
                return S;
            }
            if(degree<45 && degree >315){
                return E;
            }

            return null;
        }
    }
}
