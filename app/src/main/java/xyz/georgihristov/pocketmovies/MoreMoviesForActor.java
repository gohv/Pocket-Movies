package xyz.georgihristov.pocketmovies;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoreMoviesForActor {
    @SerializedName("cast")
    @Expose
    private List<Result> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Object> crew = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<Result> getCast() {
        return cast;
    }

    public void setCast(List<Result> cast) {
        this.cast = cast;
    }

    public List<Object> getCrew() {
        return crew;
    }

    public void setCrew(List<Object> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
