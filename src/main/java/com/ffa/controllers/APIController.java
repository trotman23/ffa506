package com.ffa.controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ffa.models.*;

@RestController
public class APIController {

	@RequestMapping("/NFLTeam")
    public NFLTeam team(@RequestParam(value="TeamID", defaultValue = "1") String id) {
        return new NFLTeam(Integer.parseInt(id));
    }
}
