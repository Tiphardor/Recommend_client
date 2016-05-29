package zju.edu.recommend.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zju.edu.recommend.activity.R;

/**
 * Created by wry2767 on 2016/5/23.
 */
public class InfoFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View infoLayout = inflater.inflate(R.layout.fragment_info,container,false);
        return infoLayout;
    }
}
