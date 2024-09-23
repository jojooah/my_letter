package com.jojo.my_letter.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RestResult {
    private Map<String, Object> data;

}
