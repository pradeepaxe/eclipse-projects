package com.accenture.swagger.addr;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping(value="/users")
public class addressController {

    ConcurrentMap<String,Contact> contacts=
            new ConcurrentHashMap<>();

    @GetMapping("/id")
    public Contact getContact(@PathVariable String id)
    {
        return contacts.get(id);

    }

    @GetMapping("/")
    public List<Contact> getContact()
    {
        return new ArrayList<Contact>(contacts.values());
    }
    @PostMapping("/")
    public Contact getContact(@RequestBody Contact contact)
    {
        contacts.put(contact.getId(),contact);
        return contact;
    }
}
