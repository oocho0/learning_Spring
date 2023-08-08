package hello.springmvc.basic.requestMapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String user(){
        return "GET user";
    }

    @PostMapping
    public String addUser(){
        return "POST user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId){
        return "GET userId = "+userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId){
        return "update userId = "+userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return "delete userId = "+userId;
    }
}
