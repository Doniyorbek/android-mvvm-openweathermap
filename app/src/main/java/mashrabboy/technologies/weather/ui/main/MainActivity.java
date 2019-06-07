package mashrabboy.technologies.weather.ui.main;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mashrabboy.technologies.weather.BuildConfig;
import mashrabboy.technologies.weather.R;
import mashrabboy.technologies.weather.data.remote.ApiFactory;
import mashrabboy.technologies.weather.data.remote.model.Region;
import mashrabboy.technologies.weather.data.remote.model.RegionGroup;
import mashrabboy.technologies.weather.ui.base.BaseActivity;
import mashrabboy.technologies.weather.utils.TinyDB;
import retrofit2.HttpException;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {

    private AppCompatButton btnOpen;
    private AppCompatButton mButtonRetry;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView mRecyclerView;
    private AppCompatEditText mEditTextIds;
    private FloatingActionButton mFABAdd;
    private AppCompatImageView mImageViewRemove;
    private AppCompatImageView mImageViewCancel;
    private RegionAdapter mRegionAdapter;
    private TinyDB mTinyDB;
    private Toolbar toolbar;
    private AppCompatTextView mTextViewNotFound;
    private ProgressBar mProgressBarMain;
    private LinearLayoutCompat mLinearLayoutError;

    private boolean removeModeAdapter = false;
    private ArrayList<Region> mRegionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ActivityCompat.requestPermissions(this, permissions, 0);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        mTinyDB = new TinyDB(this);

        mProgressBarMain = findViewById(R.id.pb);
        mTextViewNotFound = findViewById(R.id.tvNotFound);
        mImageViewRemove = findViewById(R.id.ivRemove);
        mImageViewCancel = findViewById(R.id.ivCancel);
        mLinearLayoutError = findViewById(R.id.llError);
        mButtonRetry = findViewById(R.id.btnRetry);

        mFABAdd = findViewById(R.id.fabAdd);
        mRecyclerView = findViewById(R.id.rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRegionAdapter = new RegionAdapter();
        mRecyclerView.setAdapter(mRegionAdapter);

        mImageViewCancel.setOnClickListener(v -> {
            mImageViewRemove.setImageResource(R.drawable.ic_delete_black_24dp);
            mImageViewCancel.setVisibility(View.INVISIBLE);
            mRegionAdapter = new RegionAdapter();
            mRecyclerView.setAdapter(mRegionAdapter);
            mRegionAdapter.addAll(mRegionList);
            mFABAdd.show();
        });

        mImageViewRemove.setOnClickListener(v -> {
            if (!removeModeAdapter) {
                removeModeAdapter = true;
                mImageViewRemove.setImageResource(R.drawable.ic_check_black_24dp);
                mImageViewCancel.setVisibility(View.VISIBLE);
                mRegionAdapter.setType(RegionAdapter.mRemoveType);
                mFABAdd.hide();
            } else {
                removeModeAdapter = false;
                removeCheckedItems();
                mRegionAdapter.setType(RegionAdapter.mDefaultType);
                mImageViewRemove.setImageResource(R.drawable.ic_delete_black_24dp);
                mImageViewCancel.setVisibility(View.INVISIBLE);
                mFABAdd.show();
            }
        });

        mButtonRetry.setOnClickListener(v -> {
            loadGroup();
        });

        mFABAdd.setOnClickListener(v -> {
//            Intent mIntent = new Intent(this, ForecastActivity.class);
//            startActivity(mIntent);
            AddFragment fragment = AddFragment.newInstance();
            fragment.setCancelable(false);
            fragment.show(getSupportFragmentManager(), "kukku");
        });

        if (hasCities()) {
            loadGroup();
        } else {
            mTextViewNotFound.setVisibility(View.VISIBLE);
            mProgressBarMain.setVisibility(View.GONE);
            mImageViewRemove.setVisibility(View.GONE);
        }
    }

    public void removeCheckedItems() {
        ArrayList<Region> currentRegionList = mRegionAdapter.getRegionList();
        ArrayList<String> mNewRegionStore = new ArrayList<>();

        mRegionList.clear();
        mRegionList = null;
        mRegionList = new ArrayList<>();
        mRegionList.addAll(currentRegionList);

        for (Region mRegion : currentRegionList) {
            mNewRegionStore.add(mRegion.getId().toString());
        }
        mRecyclerView.setAdapter(mRegionAdapter);
        mTinyDB.putListString("list", mNewRegionStore);

        if (mRegionList.size() == 0) {
            showNotFound();
        }

    }

    public void search(String name) {
        if (!hasCities()) {
            showLoading();
        }
        compositeDisposable.add(ApiFactory
                .getApiClient()
                .getRegion(name, "metric", BuildConfig.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResultSearch, this::handleErrorSearch));
    }

    private void handleResultSearch(Region region) {
        boolean isHasRegion = false;
        ArrayList<String> list = mTinyDB.getListString("list");
        for (String id : list) {
            if (id.equals(region.getId().toString())) {
                isHasRegion = true;
                break;
            }
        }

        if (!isHasRegion) {
            if (!hasCities()) {
                hideLoading();
                mTextViewNotFound.setVisibility(View.GONE);
                mImageViewRemove.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mFABAdd.show();
            }

            mRegionList.add(region);
            mRegionAdapter.add(region);
            list.add(region.getId().toString());
            mTinyDB.putListString("list", list);

        } else {
            showSnackBar(region.getName() + " is added");
        }
    }

    private void handleErrorSearch(Throwable t) {
        if (t instanceof HttpException) {
            HttpException mException = (HttpException) t;
            if (mException.response().code() == 404) {
                showSnackBar("City not found");
            } else {
                showSnackBar("Internet connection error");
            }
        } else {
            showSnackBar("Internet connection error");
        }
    }

    private void loadGroup() {
        ArrayList<String> list = mTinyDB.getListString("list");
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Log.d("CITYIDS", list.get(i));
            if (i == 0) {
                ids.append(list.get(i));
            } else {
                ids.append(",").append(list.get(i));
            }

        }

        Log.d("CITYIDS", ids.toString());
        showLoading();
        compositeDisposable.add(ApiFactory
                .getApiClient()
                .getGroup(ids.toString(), "metric", BuildConfig.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError));
    }

    private void handleResult(RegionGroup regionGroup) {
        hideLoading();
        mRecyclerView.setVisibility(View.VISIBLE);
        mImageViewRemove.setVisibility(View.VISIBLE);
        mFABAdd.show();

        mRegionList.addAll(regionGroup.getList());
        mRegionAdapter.addAll(mRegionList);
    }

    private void handleError(Throwable t) {
        t.printStackTrace();
        if (t instanceof HttpException) {
            showSnackBar("Error in server");
        } else {
            showSnackBar("Internet connection error");
        }
        hideLoading();
        mLinearLayoutError.setVisibility(View.VISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        snackbar.show();
    }

    private void goneAll() {
        mProgressBarMain.setVisibility(View.GONE);
        mTextViewNotFound.setVisibility(View.GONE);

    }

    public boolean hasCities() {
        ArrayList<String> mList = mTinyDB.getListString("list");
        if (mList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void showLoading() {
        mTextViewNotFound.setVisibility(View.GONE);
        mImageViewRemove.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mLinearLayoutError.setVisibility(View.GONE);
        mFABAdd.hide();
        mProgressBarMain.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        mProgressBarMain.setVisibility(View.GONE);
    }

    public void showNotFound() {
        mImageViewRemove.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mLinearLayoutError.setVisibility(View.GONE);
        mProgressBarMain.setVisibility(View.GONE);
        mTextViewNotFound.setVisibility(View.VISIBLE);
        mFABAdd.show();
    }

}
