package com.example._WebAndJDBC;

import com.example._WebAndJDBC.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ControllerDefault {

    private final ContactService contactService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());

        return "index";
    }

    @GetMapping("/create")
    public String showCreateContact(Model model) {
        model.addAttribute("contact", new Contact());

        return "create";
    }

    @PostMapping("/create")
    public String createContact(@ModelAttribute Contact contact) {
        contactService.save(contact);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditContact(@PathVariable Long id, Model model) {
        Contact contact = contactService.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "edit";
        }

        return  "redirect:/";
    }

    @PostMapping("/edit")
    public String editContacts(@ModelAttribute Contact contact) {
        contactService.update(contact);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        contactService.delete(id);

        return "redirect:/";
    }
}
