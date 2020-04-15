package pl.sgnit.ims.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/rest")
public class DataController {

    @GetMapping("/testData")
    public Dimension testData() {
        Dimension d = new Dimension(100, 200);
        return d;
    }
}
