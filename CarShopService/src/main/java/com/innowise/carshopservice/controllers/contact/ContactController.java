package com.innowise.carshopservice.controllers.contact;

import com.innowise.carshopservice.dto.contact.AddContactDto;
import com.innowise.carshopservice.dto.contact.GetContactDto;
import com.innowise.carshopservice.enums.contact.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Advertisement;
import com.innowise.carshopservice.models.Contact;
import com.innowise.carshopservice.services.advertisement.AdvertisementService;
import com.innowise.carshopservice.services.contact.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContactService contactService;

    @Autowired
    private AdvertisementService advertisementService;

    public ContactController(AdvertisementService advertisementService, ContactService contactService) {
        super();
        this.advertisementService = advertisementService;
        this.contactService = contactService;
    }

    @PostMapping(value = "/contact/addContact/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addContact(@RequestBody AddContactDto addContactDto) {
        Optional<Advertisement> advertisement = Optional.ofNullable(advertisementService.findById(addContactDto.getAdvertisementId()));
        if(!validatePhoneNumber(addContactDto.getNumber())){
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

    @GetMapping("/contact/getContactsByAdvertisementId/{id}")
    public List<GetContactDto> getContactsByAdvertisementId(@PathVariable("id") Long id) {
        List<Contact>contacts=contactService.findAllByAdvertisement_AdvertisementIdAndActivityStatus_Active(id);
        List<GetContactDto> getContactDtos = new ArrayList<>();
        for (Contact contact: contacts){
            GetContactDto getContactDto=modelMapper.map(contact, GetContactDto.class);
            getContactDtos.add(getContactDto);
        }
        return getContactDtos;
    }

    @PostMapping(value = "/contact/deleteById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable Long id) {
        contactService.softDelete(id);
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    public boolean validatePhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$");
        return pattern.matcher(number).matches() ? true : false;
    }
}
