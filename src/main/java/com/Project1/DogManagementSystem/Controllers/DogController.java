package com.Project1.DogManagementSystem.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.Project1.DogManagementSystem.Models.Dog;
import com.Project1.DogManagementSystem.Models.Trainer;
import com.Project1.DogManagementSystem.repository.DogRepositroy;
import com.Project1.DogManagementSystem.repository.TrainerRepository;

@Controller
public class DogController {

    private final DogRepositroy dogRepo;
    private final TrainerRepository trainerRepo;

    @Autowired
    public DogController(DogRepositroy dogRepo, TrainerRepository trainerRepo) {
        this.dogRepo = dogRepo;
        this.trainerRepo = trainerRepo;
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/dogHome";
    }

    @GetMapping("/dogHome")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("trainers", trainerRepo.findAll());
        return "addNewDog";
    }

    @PostMapping("/addNewDog")
    public String addNewDog(@ModelAttribute Dog dog, @RequestParam("trainerId") int id) {
        Trainer trainer = trainerRepo.findById(id).orElseThrow(() -> new RuntimeException("Trainer not found"));
        dog.setTrainer(trainer);
        dogRepo.save(dog);
        return "redirect:/dogHome";
    }

    @GetMapping("/addTrainer")
    public String addTrainer() {
        return "addNewTrainer";
    }

    @PostMapping("/trainerAdded")
    public String addNewTrainer(@ModelAttribute Trainer trainer) {
        trainerRepo.save(trainer);
        return "redirect:/dogHome";
    }

    @GetMapping("/viewModifyDelete")
    public String viewDogs(Model model) {
        model.addAttribute("dogs", dogRepo.findAll());
        return "viewDogs";
    }

    @PostMapping("/editDog")
    public String editDog(@ModelAttribute Dog dog) {
        dogRepo.save(dog);
        return "redirect:/viewModifyDelete";
    }

    @PostMapping("/deleteDog")
    public String deleteDog(@ModelAttribute Dog dog) {
        dogRepo.findById(dog.getId()).ifPresent(dogRepo::delete);
        return "redirect:/viewModifyDelete";
    }

    @GetMapping("/search")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("dog", dogRepo.findById(id).orElse(new Dog()));
        return "searchResults";
    }
}