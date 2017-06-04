package xyz.georgihristov.pocketmovies;


class Api {
    public static final String API_KEY = "1cf817d06bb857235dae21078ad0499a";
    public static final String GET_POPULAR_MOVIES = "http://api.themoviedb.org/3/discover/movie?language=en&sort_by=popularity.desc&api_key=";
    public static final String GET_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1";
    public static final String GET_UPCOMING_MOVIES = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US&page=1";
    public static final String GET_HIGHEST_RATED_MOVIES = "http://api.themoviedb.org/3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc&api_key=";
    public static final String GET_TRAILERS = "http://api.themoviedb.org/3/movie/%s/videos?api_key=";
    public static final String GET_REVIEWS = "http://api.themoviedb.org/3/movie/%s/reviews?api_key=";
    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w500";
    public static final String BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";
    static final String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%1$s";
    static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1$s/0.jpg";
    public static final String GET_CAST = " https://api.themoviedb.org/3/movie/%s/credits?api_key=" + API_KEY;
    public static final String GET_CAST_PICTURE = "https://image.tmdb.org/t/p/w138_and_h175_bestv2";


    private Api() {
        // hide implicit public constructor
    }
}
