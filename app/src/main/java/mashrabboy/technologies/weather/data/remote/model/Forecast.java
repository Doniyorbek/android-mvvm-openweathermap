package mashrabboy.technologies.weather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Forecast {

    @Expose
    @SerializedName("cod")
    public String cod;

    @Expose
    @SerializedName("message")
    public Float message;

    @Expose
    @SerializedName("cnt")
    public Integer cnt;

    @Expose
    @SerializedName("list")
    public ArrayList<ForcestHourly> list;

    @Expose
    @SerializedName("city")
    public City city;


    public static class City {
        public Integer id;
        public String name;
        public String country;
        public Integer timezone;
        public Region.Coord coord;

    }








}
