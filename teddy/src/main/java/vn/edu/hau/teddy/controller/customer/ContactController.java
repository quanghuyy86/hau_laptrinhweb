package vn.edu.hau.teddy.controller.customer;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.hau.teddy.model.Contact;
import vn.edu.hau.teddy.service.ContactService;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String Contact(final Model model) throws IOException{
        try {
            model.addAttribute("contactEntity", new Contact());
            return "home/contact";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }
    @RequestMapping(value = "/contactUs", method = RequestMethod.POST)
    public String contactUs(final @ModelAttribute("contactEntity")Contact contact,
                            final Model model) throws IOException{
        try {
            contactService.save(contact);
            model.addAttribute("contactEntity", new Contact());
            return "home/contact";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }


}
