package com.radait.mariosample.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hunmuk
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {


    @GetMapping("/api/main")
    public ResponseEntity<String> getMain() {


      return ResponseEntity.ok("Hello, Main!");
    }
}
