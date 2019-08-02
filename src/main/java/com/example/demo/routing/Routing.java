package com.example.demo.routing;

import com.example.demo.exceptions.NotFoundException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@RestController
//@SpringBootApplication
@RequestMapping("api/v1")
public class Routing {
    private static int id = 4;

    private List<Map<String, String>> list = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "That's my first post, OMG");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "How to change your life? Follow me");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "My boss is very cool person");
        }});
    }};

    private List<Map<String, String>> list2 = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "4");
            put("text", "First");
        }});
        add(new HashMap<String, String>() {{
            put("id", "5");
            put("text", "Second");
        }});
        add(new HashMap<String, String>() {{
            put("id", "6");
            put("text", "Third");
        }});
    }};

    @GetMapping("public")
    public List<Map<String, String>> checkPublicUsers() {
        return list;
    }

    @GetMapping("private")
    public List<Map<String, String>> checkPrivateUsers() {
        return list2;
    }


    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return list.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new); //Выдает 404 (Stream API)
    }

    @PostMapping()
    public List<Map<String, String>> addNew(@RequestBody String text) {
//    public List<Map<String, String>> addUser(@RequestBody Map<String, String> text) {
//        text.put("id", String.valueOf(id++));

//        list.add(text);

        list.add(new HashMap<String, String>() {{
            put("id", String.valueOf(id++));
            put("text", text);
        }});
        return list;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody String post) {
        Map<String, String> postFromDb = getOne(id);
        postFromDb.put("text", post);
//        postFromDb.put("id", id);

        return postFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> postFromDb = getOne(id);
        list.remove(postFromDb);
    }
}
//}

