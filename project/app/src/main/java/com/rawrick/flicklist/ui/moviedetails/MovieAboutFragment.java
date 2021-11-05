package com.rawrick.flicklist.ui.moviedetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.Movie;
import com.rawrick.flicklist.data.room.FLDatabaseHelper;
import com.rawrick.flicklist.databinding.FragmentMovieAboutBinding;

public class MovieAboutFragment extends Fragment {

    private FragmentMovieAboutBinding binding;

    private FLDatabaseHelper db;
    private Bundle bundle;
    private Movie movie;

    private TextView movieTagline;
    private TextView movieOverview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initData() {
        bundle = getArguments();
        db = new FLDatabaseHelper(this.getActivity());
    }

    private void initUI(View view) {
        movie = db.getMovieDetailsForID(bundle.getInt("id"));
        movieTagline = view.findViewById(R.id.movie_about_tagline);
        movieOverview = view.findViewById(R.id.movie_about_overview);
        if (!movie.getTagline().equals("")) {
            movieTagline.setText(movie.getTagline());
        } else {
            movieTagline.setHeight(0);
        }
        movieOverview.setText(movie.getOverview());
    }
}