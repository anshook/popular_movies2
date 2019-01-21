package com.udacity.ak.popularmovies2.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.udacity.ak.popularmovies2.data.Movie;

@Database(entities = {Movie.class}, version = 3)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movie";
    private static volatile MovieDatabase dbInstance;

    public static MovieDatabase getDatabase(final Context context) {
        if (dbInstance == null) {
            synchronized (MovieDatabase.class) {
                if (dbInstance == null)
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        return dbInstance;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();
}



