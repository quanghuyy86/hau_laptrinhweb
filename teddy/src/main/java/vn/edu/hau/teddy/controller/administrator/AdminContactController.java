package vn.edu.hau.teddy.controller.administrator;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hau.teddy.model.Categories;
import vn.edu.hau.teddy.model.Contact;
import vn.edu.hau.teddy.service.ContactService;


@Controller
public class AdminContactController {

    private final ContactService contactService;

    public AdminContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/admin/contact", method = RequestMethod.GET)
    public String contactList(final Model model,
                              final @Param("keyword") String keyword,
                              final @RequestParam(name = "page", defaultValue = "0") int page){
        try {
            int pageSize = 5;

            Page<Contact> contactList =  contactService.gettAllContact(page, pageSize);
            if(keyword != null){
                contactList = contactService.searchPageContact(keyword, page, pageSize);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("contact", contactList);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", contactList.getTotalPages());

            return "admin/contact/contact_admin";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }

    }
    @RequestMapping(value = "/admin/contact/delete/{id}", method = RequestMethod.GET)
    public String contactDelete(@PathVariable("id") Integer id,
                                final Model model){
        try {
            contactService.deleteById(id);
            return "redirect:/admin/contact";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }

}
