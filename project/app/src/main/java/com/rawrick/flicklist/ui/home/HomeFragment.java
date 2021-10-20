package com.rawrick.flicklist.ui.home;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;

import static com.rawrick.flicklist.data.tools.SettingsManager.setAccountID;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.account.AccountManager;
import com.rawrick.flicklist.data.movie.MovieTrending;
import com.rawrick.flicklist.data.series.SeriesTrending;
import com.rawrick.flicklist.data.tools.SettingsManager;
import com.rawrick.flicklist.data.util.MovieManager;
import com.rawrick.flicklist.data.util.SeriesManager;
import com.rawrick.flicklist.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements MovieManager.TrendingMoviesManagerListener, TrendingMoviesViewHolder.ViewHolderListener, SeriesManager.SeriesManagerListener, TrendingSeriesViewHolder.ViewHolderListener, AccountManager.AccountManagerListener, MovieManager.RatedMoviesManagerListener {

    private FragmentHomeBinding binding;

    private SwipeRefreshLayout swipeRefreshLayout;
    // movie data & adapter
    private MovieManager movieManager;
    private RecyclerView recyclerTrendingMovies;
    public TrendingMoviesAdapter trendingMoviesAdapter;
    // series data & adapter
    private SeriesManager seriesManager;
    private RecyclerView recyclerTrendingSeries;
    public TrendingSeriesAdapter trendingSeriesAdapter;
    // Views for featured trending movie
    TextView featuredTitle;
    TextView featuredOverview;
    TextView featuredScore;
    ImageView featuredPoster;
    ImageView featuredBackdrop;
    // Views for featured trending series
    TextView featuredSeriesTitle;
    TextView featuredSeriesOverview;
    TextView featuredSeriesScore;
    ImageView featuredSeriesPoster;
    ImageView featuredSeriesBackdrop;
    // Views for banner
    private AccountManager accountManager;
    ShapeableImageView userAvatar;
    TextView userName;

    ArrayList<String[]> rM;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
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

    /**
     * DATA
     */

    private void initData() {
        Log.d("FlickListApp", SettingsManager.getSessionID(this.getActivity()));
        movieManager = new MovieManager(getActivity(), this, this);
        seriesManager = new SeriesManager(getActivity(), this);
        accountManager = new AccountManager(getActivity(), this);
        accountManager.getAccountDataFromAPI();
        movieManager.getRatedMoviesFromAPI();
        getTrendingData();

    }

    private void getTrendingData() {
        movieManager.getTrendingMoviesFromAPI();
        seriesManager.getTrendingSeriesFromAPI();
    }

    /**
     * UI
     */

    private void initUI(View view) {
        /**
         * BANNER
         */
        userName = view.findViewById(R.id.home_header_name);
        userAvatar = view.findViewById(R.id.home_header_avatar);

        /**
         * MOVIES
         **/
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
        /**
         * SERIES
         **/
        recyclerTrendingSeries = view.findViewById(R.id.home_trending_series_container);
        recyclerTrendingSeries.setLayoutManager(new LinearLayoutManager(getActivity(), HORIZONTAL, false));
        trendingSeriesAdapter = new TrendingSeriesAdapter(getActivity(), this);
        recyclerTrendingSeries.setAdapter(trendingSeriesAdapter);
        // Views for featured movie
        featuredSeriesTitle = view.findViewById(R.id.home_trending_series_title);
        featuredSeriesOverview = view.findViewById(R.id.home_trending_series_description);
        featuredSeriesScore = view.findViewById(R.id.home_trending_series_score);
        featuredSeriesPoster = view.findViewById(R.id.home_trending_series_poster);
        featuredSeriesBackdrop = view.findViewById(R.id.home_featured_series_backdrop);

        swipeRefreshLayout = view.findViewById(R.id.home_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTrendingData();
                onTrendingMoviesUpdated();
                onTrendingSeriesUpdated();
                trendingMoviesAdapter.notifyDataSetChanged();
                trendingSeriesAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //DEBUG
        FloatingActionButton fab = view.findViewById(R.id.explore_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rM = movieManager.getRatedMovies();
                for (String[] movie : rM) {
                    Log.d("FlickListApp", "id: " + movie[0] + ", rating: " + movie[1]);
                }

            }
        });
    }

    @Override
    public void onTrendingMoviesUpdated() {
        trendingMoviesAdapter.setMoviesTrending(movieManager.getMoviesTrending());
        // sets default featured movie
        setFeaturedMovieDetails(0);
    }

    @Override
    public void onTrendingMovieClicked(int position) {
        setFeaturedMovieDetails(position);
    }

    // provides details for featured view of a movie
    private void setFeaturedMovieDetails(int position) {
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

    @Override
    public void onTrendingSeriesUpdated() {
        trendingSeriesAdapter.setSeriesTrending(seriesManager.getTrendingSeries());
        // sets default featured series
        setFeaturedSeriesDetails(0);
    }

    @Override
    public void onTrendingSeriesClicked(int position) {
        setFeaturedSeriesDetails(position);
    }

    // provides details for featured view of a series
    private void setFeaturedSeriesDetails(int position) {
        SeriesTrending selectedSeries = seriesManager.getTrendingSeries().get(position);
        // sets title
        featuredSeriesTitle.setText(selectedSeries.getTitle());
        // sets overview
        String overview = selectedSeries.getOverview();
        if (selectedSeries.getOverview().length() > 230) {
            overview = selectedSeries.getOverview().substring(0, 230) + "...";
        }
        featuredSeriesOverview.setText(overview);
        // sets vote average
        int voteAverage = (int) (selectedSeries.getVoteAverage() * 10);
        featuredSeriesScore.setText(voteAverage + "%");
        // sets poster
        Glide.with(getActivity())
                .load(selectedSeries.getPosterPath())
                .centerCrop()
                .into(featuredSeriesPoster);
        // sets backdrop
        Glide.with(getActivity())
                .load(selectedSeries.getBackdropPath())
                .centerCrop()
                .into(featuredSeriesBackdrop);
    }

    @Override
    public void onAccountDataUpdated() {
        setAccountID(this.getActivity(), accountManager.getAccountData()[0]);
        String welcomeText = "Hello, " + accountManager.getAccountData()[1] + ".";
        userName.setText(welcomeText);
        Glide.with(getActivity())
                .load(accountManager.getAccountData()[3])
                .centerCrop()
                .into(userAvatar);
    }

    @Override
    public void onRatedMoviesUpdated() {
        rM = movieManager.getRatedMovies();
    }
}