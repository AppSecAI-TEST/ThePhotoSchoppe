package com.example.acer.thephotoschoppe.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.thephotoschoppe.R;

import java.util.Collection;
import java.util.Collections;

import javax.xml.parsers.ParserConfigurationException;


/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends Fragment {


    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_portfolio, container, false);


//        String apiKey = "4ae7f11ac76cf91aaa49f128e13dacb2";
//        String sharedSecret = "bff58851c7cd5fdd";
//        Flickr f = null;
//        try {
//            f = new Flickr(apiKey, sharedSecret, new REST());
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//        TestInterface testInterface = f.getTestInterface();
//
//        Collection results = testInterface.echo(Collections.EMPTY_MAP);
        return rootView;
    }

}
