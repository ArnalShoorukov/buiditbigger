package com.example.arnal.mosquefinder.backend;

import com.example.JokeTell;

/**
 * Created by arnal on 6/20/17.
 */

public class Joke {

    private JokeTell jokeTell;

  public Joke(){
      jokeTell = new JokeTell();
  }

  public String getData(){
      return jokeTell.getJoke();
  }
}
