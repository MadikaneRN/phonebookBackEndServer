package za.ac.cput.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import za.ac.cput.entity.Entry;
import za.ac.cput.repository.EntryRepository;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*",allowedHeaders ="*" )
public class HomeController {

    private EntryRepository repository;

    @Autowired
    public HomeController(EntryRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model) {
        List<Entry> entries = repository.findAll();
        model.addAttribute("entries", entries);
        model.addAttribute("insertEntry", new Entry());
        return "home";
    }

    @RequestMapping(value = "/entry/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entry> getEntry(@PathVariable("id") long id) {
        Entry record = repository.findOne(id);
        if (record == null) {
            return new ResponseEntity<Entry>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Entry>(record, HttpStatus.OK);
    }


    //---------------------Retrieve List of Entries---------------------------------------------------
    @RequestMapping(value = "/entries/",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Entry>> getOneEntry()
    {
        List<Entry> entry = repository.findAll();
        if(entry.isEmpty())
        {
            return new ResponseEntity<List<Entry>>(HttpStatus.NO_CONTENT);//OR HttpStatus.Not_Found
        }

        return new ResponseEntity<List<Entry>>(entry,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String insertData(ModelMap model,
                             @ModelAttribute("insertEntry") @Valid Entry entry,
                             BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(entry);
        }
        return home(model);
    }


    //--------------------------------------Create Entry------------------------------------
    @RequestMapping(value = "/entry/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createEntry(@RequestBody Entry entry, UriComponentsBuilder ucBuilder)
    {
        repository.save(entry);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/entry/{id}").buildAndExpand(entry.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }




    //--------------------------------------Delete Entry------------------------------------
    @RequestMapping(value = "/entry/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Entry> deleteEntry(@PathVariable("id")long id)
    {
        System.out.println("Fetching & Deleting Adoption with id" + id);
        Entry entry = repository.findOne(id);
        if(entry  == null)
        {
            System.out.println("Unable to delete. Adoption with id " + id + " not found");//comment
            return new ResponseEntity<Entry>(HttpStatus.NOT_FOUND);

        }

        repository.delete(entry);
        return new ResponseEntity<Entry>(HttpStatus.NO_CONTENT);
    }
}
