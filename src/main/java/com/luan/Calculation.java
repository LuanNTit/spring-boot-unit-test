package com.luan;

import org.springframework.stereotype.Service;

@Service
public class Calculation {

  public int sum(int a, int b) {
    return a + b;
  }
  public int multiply(int a, int b) {
	  return a * b;
  }
  public int divice(int a, int b) {
	  return a / b;
  }
}