package com.luan;

import org.springframework.stereotype.Component;

@Component
public class Hello {

  public String say(String name) {
    return "Hello " + name;
  }
}
