package pl.denisolek;

import org.springframework.web.bind.annotation.*;

@RestController
public class Hello {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String Hello(){
        return "hello";
    }

}
