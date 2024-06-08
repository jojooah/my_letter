package com.jojo.my_letter.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class RestError {
  private final String code;
  private final String message;
  private String detailMessage;
}
