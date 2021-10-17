package com.rawrick.flicklist.ui.home;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.util.MovieManager;
import com.rawrick.flicklist.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements MovieManager.MovieManagerListener, TrendingMoviesViewHolder.ViewHolderListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private RequestQueue mQueue;

    private MovieManager movieManager;
    private RecyclerView recyclerTrendingMovies;
    public TrendingMoviesAdapter trendingMoviesAdapter;

    // Views for featured trending movie
    TextView featuredTitle;
    TextView featuredOverview;
    TextView featuredScore;
    ImageView featuredPoster;
    ImageView featuredBackdrop;
    //
    ImageView trendingMovieFrame;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        movieManager = new MovieManager(getActivity(), this);
        movieManager.getMoviesFromURL();
        Log.d("getmovies", String.valueOf(movieManager.getMoviesTrending()));
    }

    private void initUI(View view) {
        // initialize trending movies adapter
        recyclerTrendingMovies = view.findViewById(R.id.home_trending_movies_container);
        recyclerTrendingMovies.setLayoutManager(new LinearLayoutManager(getActivity(), HORIZONTAL, false));
        trendingMoviesAdapter = new TrendingMoviesAdapter(getActivity(), this);
        recyclerTrendingMovies.setAdapter(trendingMoviesAdapter);
        // Views for featured movie
        featuredTitle = view.findViewById(R.id.home_trending_view_title);
        featuredOverview = view.findViewById(R.id.home_trending_view_description);
        featuredScore = view.findViewById(R.id.home_trending_view_score);
        featuredPoster = view.findViewById(R.id.home_trending_view_poster);
        featuredBackdrop = view.findViewById(R.id.home_featured_backdrop);
        //
        trendingMovieFrame = view.findViewById(R.id.home_trending_movie_item_frame);
    }

    @Override
    public void onMoviesUpdated() {
        trendingMoviesAdapter.setMoviesTrending(movieManager.getMoviesTrending());
        // sets default featured movie
        setFeaturedDetails(0);
        // setSelectedFrame(0);
    }

    @Override
    public void onViewClicked(int position) {
        setFeaturedDetails(position);

    }

    // provides details for featured view of a movie
    private void setFeaturedDetails(int position) {
        MovieTrending selectedMovie = movieManager.getMoviesTrending().get(position);
        // sets title
        featuredTitle.setText(selectedMovie.getTitle());
        // sets overview
        String overview = selectedMovie.getOverview();
        if (selectedMovie.getOverview().length() > 230) {
            overview = selectedMovie.getOverview().substring(0, 230) + "...";
        }
        featuredOverview.setText(overview);
        // sets vote average
        int voteAverage = (int) (selectedMovie.getVoteAverage() * 10);
        featuredScore.setText(voteAverage + "%");
        // sets poster
        Glide.with(getActivity())
                .load(selectedMovie.getPosterPath())
                .centerCrop()
                .into(featuredPoster);
        // sets backdrop
        Glide.with(getActivity())
                .load(selectedMovie.getBackdropPath())
                .centerCrop()
                .into(featuredBackdrop);
    }

}