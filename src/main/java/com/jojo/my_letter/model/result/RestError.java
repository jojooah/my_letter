package com.jojo.my_letter.model.result;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RestError {
  private final String code;
  private final String message;
  private String detailMessage;
}
