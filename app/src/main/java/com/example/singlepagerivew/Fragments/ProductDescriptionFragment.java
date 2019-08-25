package com.example.singlepagerivew.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.example.singlepagerivew.R;

public class ProductDescriptionFragment extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        webView = view.findViewById(R.id.descView);
        webView.loadData(getArguments().getString("html"), "text/html; charset=UTF-8", null);
        return view;
    }
}
