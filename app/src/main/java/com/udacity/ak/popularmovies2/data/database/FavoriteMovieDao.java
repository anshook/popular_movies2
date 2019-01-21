package com.udacity.ak.popularmovies2.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.udacity.ak.popularmovies2.data.Movie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movie ORDER BY originalTitle")
    LiveData<List<Movie>> loadAllFavorites();

    @Query("SELECT id FROM favorite_movie")
    List<Long> getAllFavoriteIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavorite(Movie favorite);

    @Delete
    void deleteFavorite(Movie favorite);

    @Query("DELETE FROM favorite_movie WHERE id = :id")
    void deleteById(long id);

}
