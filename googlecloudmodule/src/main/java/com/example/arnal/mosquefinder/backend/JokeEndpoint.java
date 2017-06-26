package com.example.arnal.mosquefinder.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "backend.mosquefinder.arnal.example.com",
                ownerName = "backend.mosquefinder.arnal.example.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    @ApiMethod(name = "newJoke")
    public Joke sayJoke(Joke jjoke) {
        return jjoke;
    }
}