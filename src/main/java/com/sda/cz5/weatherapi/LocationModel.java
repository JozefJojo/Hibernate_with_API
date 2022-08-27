package com.sda.cz5.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationModel {
    private String name;

    Local_names Local_namesObject;
    private float lat;
    private float lon;
    private String country;


    // Getter Methods

    public String getName() {
        return name;
    }

    public Local_names getLocal_names() {
        return Local_namesObject;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getCountry() {
        return country;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setLocal_names(Local_names local_namesObject) {
        this.Local_namesObject = local_namesObject;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Local_names {
        private String cs;
        private String de;
        private String pl;


        // Getter Methods

        public String getCs() {
            return cs;
        }

        public String getDe() {
            return de;
        }

        public String getPl() {
            return pl;
        }

        // Setter Methods

        public void setCs(String cs) {
            this.cs = cs;
        }

        public void setDe(String de) {
            this.de = de;
        }

        public void setPl(String pl) {
            this.pl = pl;
        }
    }
}