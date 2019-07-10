package mashrabboy.technologies.weather.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import mashrabboy.technologies.weather.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        attachFragment();
    }

    public void attachFragment() {
        DetailsFragment mDetailsFragment = DetailsFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,mDetailsFragment,"details")
                .commit();
    }

}
