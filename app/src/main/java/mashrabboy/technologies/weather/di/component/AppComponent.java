package mashrabboy.technologies.weather.di.component;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import mashrabboy.technologies.weather.WeatherApp;
import mashrabboy.technologies.weather.di.builder.ActivityBuilder;
import mashrabboy.technologies.weather.di.module.AppModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(WeatherApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

