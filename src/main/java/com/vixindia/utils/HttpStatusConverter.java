package com.vixindia.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HttpStatusConverter {
  public HttpStatus integerToHttpStatus(Integer value) {

    Map<Integer, HttpStatus> map = new LinkedHashMap<>();

    map.put(200, HttpStatus.OK);
    map.put(400, HttpStatus.BAD_REQUEST);
    map.put(404, HttpStatus.NOT_FOUND);
    map.put(500, HttpStatus.INTERNAL_SERVER_ERROR);

    return map.get(value);

  }
}
