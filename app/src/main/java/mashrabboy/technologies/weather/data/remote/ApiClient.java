package mashrabboy.technologies.weather.data.remote;

import io.reactivex.Observable;
import mashrabboy.technologies.weather.data.remote.model.Region;
import mashrabboy.technologies.weather.data.remote.model.RegionGroup;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("group?")
    Observable<RegionGroup> getGroup(
            @Query("id") String ids,
            @Query("units") String units,
            @Query("appid") String appId);

    @GET("weather?")
    Observable<Region> getRegion(
            @Query("q") String name,
            @Query("units") String units,
            @Query("appid") String appId);


}
