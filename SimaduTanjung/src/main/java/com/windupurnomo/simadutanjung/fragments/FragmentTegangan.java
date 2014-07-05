package com.windupurnomo.simadutanjung.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windupurnomo.simadutanjung.R;

/**
 * Created by Windu Purnomo on 7/5/14.
 */
public class FragmentTegangan extends Fragment {
    public FragmentTegangan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tegangan, container, false);
        return rootView;
    }
}