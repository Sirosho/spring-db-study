package com.spring.database.querydsl.api;

import com.spring.database.querydsl.dto.GroupAverageAge;
import com.spring.database.querydsl.service.IdolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/query/idols")
public class IdolController {

    private final IdolService idolService;

    @GetMapping("/avg")
    public ResponseEntity<?> average(){

        return ResponseEntity.ok().body(idolService.average());



    }




}
