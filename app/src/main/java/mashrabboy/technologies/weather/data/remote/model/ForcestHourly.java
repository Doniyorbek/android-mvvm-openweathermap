package mashrabboy.technologies.weather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForcestHourly {

    @Expose
    @SerializedName("dt")
    public Integer dt;

    @Expose
    @SerializedName("dt_txt")
    public String dt_txt;

    @Expose
    @SerializedName("main")
    public Main main;

    @Expose
    @SerializedName("weather")
    public Region.Weather[] weather;

    @Expose
    @SerializedName("wind")
    public Region.Wind wind;

    @Expose
    @SerializedName("clouds")
    public Region.Clouds clouds;

    @Expose
    @SerializedName("sys")
    public Sys sys;

    public static class Main {
        public Float temp;
        public Float pressure;
        public int humidity;
        public Float temp_min;
        public Float temp_max;
        public Float sea_level;
        public Float grnd_level;
        public Float temp_kf;
    }

    public static class Sys {
        public String pod;
    }

}
