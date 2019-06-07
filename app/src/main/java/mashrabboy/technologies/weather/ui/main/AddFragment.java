package mashrabboy.technologies.weather.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import mashrabboy.technologies.weather.R;

public class AddFragment extends DialogFragment {

    MainActivity mMainActivity;

    public static AddFragment newInstance() {
        Bundle args = new Bundle();
        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mMainActivity = (MainActivity) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog mDialog = super.onCreateDialog(savedInstanceState);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return mDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_add, container, false);
        AppCompatButton mButtonClose = mView.findViewById(R.id.btnClose);
        AppCompatButton mButtonAdd = mView.findViewById(R.id.btnAdd);
        AppCompatEditText mEditTextRegionname = mView.findViewById(R.id.etAdd);

        mButtonClose.setOnClickListener(v -> {
            dismiss();
        });

        mButtonAdd.setOnClickListener(v -> {
            String mRegionName = mEditTextRegionname.getText().toString();
            if (!mRegionName.isEmpty()) {
                mMainActivity.search(mRegionName);
            } else {
                Toast.makeText(mMainActivity, "Region name is empty. Please enter it", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        return mView;
    }
}
