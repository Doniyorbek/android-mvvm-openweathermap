package mashrabboy.technologies.weather.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import mashrabboy.technologies.weather.R;
import mashrabboy.technologies.weather.ui.base.BaseFragment;

public class DetailsFragment extends BaseFragment {

    private RecyclerView mRecyclerViewHourly;

    public static DetailsFragment newInstance() {
        Bundle args = new Bundle();
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_details,container,false);
        return mView;
    }

    private void loadData() {

    }
}
