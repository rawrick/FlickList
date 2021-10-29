package com.rawrick.flicklist.ui.moviedetails;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rawrick.flicklist.R;
import com.rawrick.flicklist.data.util.MovieManager;
import com.rawrick.flicklist.databinding.FragmentMovieCastBinding;


public class MovieCastFragment extends Fragment implements MovieManager.MovieDetailsManagerListener, MovieManager.TrendingMoviesManagerListener, MovieManager.RatedMoviesManagerListener, MovieManager.WatchlistedMoviesManagerListener, MovieManager.MovieCastManagerListener, MovieCastViewHolder.ViewHolderListener {

    private FragmentMovieCastBinding binding;



    private MovieManager movieManager;
    private RecyclerView movieCastRecyclerView;
    private MovieCastAdapter movieCastAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieCastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initData() {
        movieManager = new MovieManager(getActivity(), this, this, this, this, this);
        movieManager.getMovieCastFromAPI();
    }

    private void initUI(View view) {
        final int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_list_spacing) / 2;
        movieCastRecyclerView = view.findViewById(R.id.movie_cast_container);
        movieCastRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        movieCastRecyclerView.setPadding(spacing, spacing, spacing, spacing);
        movieCastRecyclerView.setClipToPadding(false);
        movieCastRecyclerView.setClipChildren(false);
        movieCastRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });

        movieCastAdapter = new MovieCastAdapter(getActivity(), this);
        movieCastRecyclerView.setAdapter(movieCastAdapter);
    }

    @Override
    public void onMovieCastUpdated() {
        movieCastAdapter.setMovieCast(movieManager.getMovieCast());
    }

    @Override
    public void onTrendingMoviesUpdated() {

    }

    @Override
    public void onRatedMoviesUpdated() {

    }

    @Override
    public void onWatchlistedMoviesUpdated() {

    }

    @Override
    public void onMovieDetailsUpdated() {

    }

    @Override
    public void onMovieCastItemClicked(int position) {

    }
}