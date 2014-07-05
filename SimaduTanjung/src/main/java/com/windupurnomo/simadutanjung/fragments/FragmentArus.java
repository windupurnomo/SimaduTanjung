package com.windupurnomo.simadutanjung.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windupurnomo.simadutanjung.R;

/**
 * Created by Windu Purnomo on 7/5/14.
 */
public class FragmentArus extends android.support.v4.app.Fragment{
    public FragmentArus() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_arus, container, false);
        return rootView;
    }
}