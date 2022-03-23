package com.zamuraev.aggregatorservice.service;

import com.zamuraev.aggregatorservice.dto.RecommendedMovie;
import com.zamuraev.aggregatorservice.dto.UserGenre;
import com.zamuraev.grpcflix.protomodels.common.Genre;
import com.zamuraev.grpcflix.protomodels.movie.MovieSearchRequest;
import com.zamuraev.grpcflix.protomodels.movie.MovieSearchResponse;
import com.zamuraev.grpcflix.protomodels.movie.MovieServiceGrpc;
import com.zamuraev.grpcflix.protomodels.user.UserGenreUpdateRequest;
import com.zamuraev.grpcflix.protomodels.user.UserResponse;
import com.zamuraev.grpcflix.protomodels.user.UserSearchRequest;
import com.zamuraev.grpcflix.protomodels.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId){
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = this.userStub.getUserGenre(userSearchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMovieList()
                .stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre){
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build();
        UserResponse userResponse = this.userStub.updateUserGenre(userGenreUpdateRequest);
    }


}
