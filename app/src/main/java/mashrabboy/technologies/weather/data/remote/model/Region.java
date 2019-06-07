package mashrabboy.technologies.weather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Region {

    @Expose
    @SerializedName("cod")
    public String cod;

    @Expose
    @SerializedName("message")
    public String message;


    @Expose
    @SerializedName("coord")
    public Coord coord;

    @Expose
    @SerializedName("sys")
    public Sys sys;

    @Expose
    @SerializedName("weather")
    public Weather[] weather;

    @Expose
    @SerializedName("main")
    public Main main;

    @Expose
    @SerializedName("wind")
    public Wind wind;

    @Expose
    @SerializedName("clouds")
    public Clouds clouds;

    @Expose
    @SerializedName("visibility")
    public Double visibility;


    @Expose
    @SerializedName("dt")
    public Long dt;

    @Expose
    @SerializedName("id")
    public Long id;

    @Expose
    @SerializedName("name")
    public String name;

    public boolean remove = false;

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        remove = remove;
    }

    public Coord getCoord() {
        return coord;
    }

    public Sys getSys() {
        return sys;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Double getVisibility() {
        return visibility;
    }

    public Long getDt() {
        return dt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Coord {
        public Double lon;
        public Double lat;
    }

    public static class Sys {
        public Integer type;
        public Integer id;
        public Double message;
        public String country;
        public Long sunrise;
        public Long sunset;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getMessage() {
            return message;
        }

        public void setMessage(Double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Long getSunrise() {
            return sunrise;
        }

        public void setSunrise(Long sunrise) {
            this.sunrise = sunrise;
        }

        public Long getSunset() {
            return sunset;
        }

        public void setSunset(Long sunset) {
            this.sunset = sunset;
        }
    }

    public static class Weather {
        public Long id;
        public String main;
        public String description;
        public String icon;
    }

    public static class Main {
        public Float temp;
        public Float pressure;
        public int humidity;
        public Float temp_min;
        public Float temp_max;

        public Float getTemp() {
            return temp;
        }

        public Float getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public Float getTemp_min() {
            return temp_min;
        }

        public Float getTemp_max() {
            return temp_max;
        }
    }

    public static class Wind {
        public Float speed;
        public Float deg;
    }

    public static class Clouds {
        public Float all;
    }
}
