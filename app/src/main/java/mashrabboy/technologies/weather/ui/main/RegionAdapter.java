package mashrabboy.technologies.weather.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import mashrabboy.technologies.weather.BuildConfig;
import mashrabboy.technologies.weather.R;
import mashrabboy.technologies.weather.data.remote.model.Region;

public class RegionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int mRemoveType = 0;
    public static final int mDefaultType = 1;

    private ArrayList<Region> mRegionList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mCurrentType = mDefaultType;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        RecyclerView.ViewHolder mViewHolder = null;
        mLayoutInflater = LayoutInflater.from(mContext);
        if (viewType == mDefaultType) {
            View mView = mLayoutInflater.inflate(R.layout.item_region, parent, false);
            mViewHolder = new ItemRegion(mView);
        } else {
            View mView = mLayoutInflater.inflate(R.layout.item_region_remove, parent, false);
            mViewHolder = new ItemRegionRemove(mView);
        }
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Region region = mRegionList.get(position);

        if (getItemViewType(position) == mDefaultType) {
            ItemRegion itemRegion = (ItemRegion) holder;


            Locale l = new Locale("en", region.getSys().country);
            String countryName = l.getDisplayCountry();

            itemRegion.tvName.setText(region.name);
            itemRegion.tvCountryName.setText(countryName);
            itemRegion.tvHumidity.setText("Humidity " + region.getMain().getHumidity() + "%");
            itemRegion.tvSpeed.setText(String.format(mContext.getString(R.string.speed), (region.getWind().speed * 3.6)));
            itemRegion.tvBetween.setText("" + Math.round(region.getMain().getTemp_min()) + " / " + Math.round(region.getMain().getTemp_max()) + "\u2103");
            itemRegion.tvCurrentTemp.setText("" + Math.round(region.getMain().getTemp()) + "℃");

            Date date = new Date(region.getSys().sunrise * 1000L);
            SimpleDateFormat format = new SimpleDateFormat("hh:mm", Locale.getDefault());
            String mSunRiseTime = format.format(date);
            itemRegion.tvSunRise.setText(String.format(mContext.getString(R.string.sun_rise), mSunRiseTime));
            date = new Date(region.getSys().getSunset() * 1000L);
            String mSunSetTime = format.format(date);
            itemRegion.tvSunSet.setText(String.format(mContext.getString(R.string.sun_set), mSunSetTime));
            itemRegion.tvPressure.setText(String.format(mContext.getString(R.string.pressure), region.getMain().getPressure() / 1.333));


            Glide.with(mContext).load(BuildConfig.BASE_URL_IMAGE + region.getWeather()[0].icon + ".png").into(itemRegion.ivIcon);
        } else {

            ItemRegionRemove itemRegion = (ItemRegionRemove) holder;
            Locale l = new Locale("en", region.getSys().country);
            String countryName = l.getDisplayCountry();
            itemRegion.tvName.setText(region.name);
            itemRegion.tvCountryName.setText(countryName);
            itemRegion.clItem.setOnClickListener(v -> {
                removeAt(position);
            });
        }

    }

    public void removeAt(int position) {
        mRegionList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mCurrentType;
    }

    public ArrayList<Region> getRegionList() {
        return mRegionList;
    }

    public void setType(int type) {
        mCurrentType = type;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRegionList.size();
    }

    public void addAll(ArrayList<Region> mNewList) {
        for (Region mRegion : mNewList) {
            add(mRegion);
        }
    }

    public void add(Region mRegion) {
        mRegionList.add(mRegion);
        notifyItemInserted(mRegionList.size() - 1);
        notifyDataSetChanged();
    }

    static class ItemRegion extends RecyclerView.ViewHolder {
        AppCompatTextView tvName;
        AppCompatTextView tvCountryName;
        AppCompatTextView tvHumidity;
        AppCompatTextView tvCurrentTemp;
        AppCompatTextView tvSpeed;
        AppCompatTextView tvBetween;
        AppCompatTextView tvSunRise;
        AppCompatTextView tvSunSet;
        AppCompatTextView tvPressure;
        AppCompatImageView ivIcon;

        public ItemRegion(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvSpeed = itemView.findViewById(R.id.tvSpeed);
            tvCurrentTemp = itemView.findViewById(R.id.tvCurrent);
            tvBetween = itemView.findViewById(R.id.tvBetween);
            tvSunRise = itemView.findViewById(R.id.tvSunRise);
            tvSunSet = itemView.findViewById(R.id.tvSunSet);
            tvPressure = itemView.findViewById(R.id.tvPressure);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }

    static class ItemRegionRemove extends RecyclerView.ViewHolder {
        AppCompatTextView tvName;
        AppCompatTextView tvCountryName;
        AppCompatImageView ivRemove;
        ConstraintLayout clItem;

        public ItemRegionRemove(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            clItem = itemView.findViewById(R.id.clItem);
        }
    }

}
