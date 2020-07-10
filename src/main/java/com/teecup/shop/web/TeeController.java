package com.teecup.shop.web;

import com.teecup.shop.model.Tee;
import com.teecup.shop.repository.TeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeeController {

    @Autowired
    private TeeRepository teeRepo;

    /**
     * Load the new widget page.
     * @param model
     * @return
     */
    @GetMapping("/tee/new")
    public String newWidget(Model model) {
        model.addAttribute("tee", new Tee());
        return "teeform";
    }

    /**
     * Create a new widget.
     * @param widget
     * @param model
     * @return
     */
    @PostMapping("/tee")
    public String createWidget(Tee tee, Model model) {
    	teeRepo.save(tee);
        return "redirect:/tee/" + tee.getId();
    }

    /**
     * Get a widget by ID.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/widget/{id}")
    public String getWidgetById(@PathVariable Long id, Model model) {
        model.addAttribute("tee", teeRepo.findById(id).orElse(new Tee()));
        return "tee";
    }

    /**
     * Get all widgets.
     * @param model
     * @return
     */
    @GetMapping("/tees")
    public String getTee(Model model) {
        model.addAttribute("tees", teeRepo.findAll());
        return "tees";
    }

    /**
     * Load the edit widget page for the widget with the specified ID.
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/widget/edit/{id}")
    public String editTee(@PathVariable Long id, Model model) {
        model.addAttribute("tee", teeRepo.findById(id).orElse(new Tee()));
        return "teeform";
    }

    /**
     * Update a widget.
     * @param widget
     * @return
     */
    @PostMapping("/tee/{id}")
    public String updateTee(Tee tee) {
    	teeRepo.save(tee);
        return "redirect:/widget/" + tee.getId();
    }

    /**
     * Delete a widget by ID.
     * @param id
     * @return
     */
    @GetMapping("/tee/delete/{id}")
    public String deleteTee(@PathVariable  Long id) {
    	teeRepo.deleteById(id);
        return "redirect:/tee";
    }
}
