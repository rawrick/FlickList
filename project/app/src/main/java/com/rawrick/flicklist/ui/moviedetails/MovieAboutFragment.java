package com.rawrick.flicklist.ui.moviedetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.databinding.FragmentMovieAboutBinding;


public class MovieAboutFragment extends Fragment {

    private FragmentMovieAboutBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}