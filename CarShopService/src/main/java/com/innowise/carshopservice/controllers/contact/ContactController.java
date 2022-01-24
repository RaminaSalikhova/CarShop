package com.innowise.carshopservice.controllers.contact;

import com.innowise.carshopservice.dto.contact.AddContactDto;
import com.innowise.carshopservice.dto.contact.GetContactDto;
import com.innowise.carshopservice.enums.contact.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Advertisement;
import com.innowise.carshopservice.models.Contact;
import com.innowise.carshopservice.services.advertisement.AdvertisementService;
import com.innowise.carshopservice.services.contact.ContactService;
import com.innowise.carshopservice.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final ContactService contactService;

    @Autowired
    private final AdvertisementService advertisementService;

    public ContactController(AdvertisementService advertisementService, ContactService contactService) {
        super();
        this.advertisementService = advertisementService;
        this.contactService = contactService;
    }

    @PostMapping(value = "/advertisements/{id}/contacts/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addContact(@RequestParam("id") Long id, @RequestBody AddContactDto addContactDto) {
        Optional<Advertisement> advertisement = Optional.ofNullable(advertisementService.findById(id));
        if(!ValidationUtil.validatePhoneNumber(addContactDto.getNumber())){
            return new ResponseEntity<>("No valid number", HttpStatus.BAD_REQUEST);
        }
        if (advertisement.isPresent()) {
            Contact contact=new Contact();
            contact.setNumber(addContactDto.getNumber());
            contact.setAdvertisement(advertisement.get());
            contact.setActivityStatus(ACTIVITY_STATUS.active);
            contactService.save(contact);
            return new ResponseEntity<>("Successful operation", HttpStatus.OK);
        }
        return new ResponseEntity<>("No such advertisement", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/advertisements/{id}/contacts/")
    public List<GetContactDto> getContactsByAdvertisementId(@PathVariable("id") Long id) {
        List<Contact>contacts=contactService.findAllByAdvertisementId(id);
        List<GetContactDto> getContactDtos = new ArrayList<>();
        for (Contact contact: contacts){
            GetContactDto getContactDto=modelMapper.map(contact, GetContactDto.class);
            getContactDtos.add(getContactDto);
        }
        return getContactDtos;
    }

    @DeleteMapping(value = "/contacts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable Long id) {
        contactService.softDelete(id);
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }
}
