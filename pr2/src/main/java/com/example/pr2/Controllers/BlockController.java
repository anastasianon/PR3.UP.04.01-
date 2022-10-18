package com.example.pr2.Controllers;

import com.example.pr2.Models.Post;
import com.example.pr2.Models.Prepod;
import com.example.pr2.Models.Student;
import com.example.pr2.repo.PostRepos;
import com.example.pr2.repo.PrepodRepos;
import com.example.pr2.repo.StudentRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BlockController {
    @Autowired
    private PostRepos postRepos;

    @Autowired
    private PrepodRepos prepodRepos;

    @Autowired
    private StudentRepos studentRepos;

    @GetMapping("/")
    public String Main(Model model){
        return "Home";
    }

    @GetMapping("/block")
    public String GetBlog(Model model){
        Iterable<Post> posts = postRepos.findAll();
        model.addAttribute("posts", posts);
        return "block-main";

    }
    @GetMapping("/student")
    public String GetStudent(Model model){
        Iterable<Student> students = studentRepos.findAll();
        model.addAttribute("students",students);
        return "student-main";
    }
    @GetMapping("/prepod")
    public String GetPrepod(Model model){
        Iterable<Prepod> prepods = prepodRepos.findAll();
        model.addAttribute("prepods",prepods);
        return "prepod-main";
    }
    @GetMapping("/block/add")
    public String blogAdd(Model model){
        return "block-add";
    }
    @GetMapping("/student/add")
    public String studentAdd(Model model){
        return "student-add";
    }
    @GetMapping("/prepod/add")
    public String prepodAdd(Model model){
        return "prepod-add";
    }

    @PostMapping("/block/add")
    public String blogPostAdd(@RequestParam(value = "title") String title,
                              @RequestParam(value = "anons") String anons,
                              @RequestParam(value = "full_text") String full_text, Model model){
        Post post = new Post(title,anons,full_text);
        postRepos.save(post);
        return "redirect:/block";
    }
    @PostMapping("/student/add")
    public Object studentPostAdd(@RequestParam(value = "Surname") String Surname,
                                  @RequestParam(value = "Name") String Name,
                                  @RequestParam(value = "Dormitories") Boolean Dormitories,
                                  @RequestParam(value = "Group") Integer Groups,
                                  @RequestParam(value = "Birthday", defaultValue = "2000-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date Birthday, Model model){

        Student student = new Student(Surname,Name, Groups,Birthday,Dormitories);
        studentRepos.save(student);
        return "redirect:/student";
    }
    @PostMapping("/prepod/add")
    public Object prepodPostAdd(@RequestParam(value = "Surname") String Surname,
                                @RequestParam(value = "Name") String Name,
                                @RequestParam(value = "Salary") Double Salary,
                                @RequestParam(value = "Predmets") String Predmets,
                                @RequestParam(value = "OpeningHours") Integer OpeningHours, Model model){
        Prepod prepod = new Prepod(Surname,Name,Salary,Predmets,OpeningHours);
        prepodRepos.save(prepod);
        return "redirect:/prepod";
    }
    @GetMapping("/block/filter")
    public String blogFilter(Model model){
        return "block-filter";
    }
    @GetMapping("/student/filter")
    public String studentFilter(Model model){
        return "student-filter";
    }
    @GetMapping("/prepod/filter")
    public String prepodFilter(Model model){
        return "prepod-filter";
    }

    @PostMapping("/block/filter/result")
    public String blogResult(@RequestParam String title, Model model){
        List<Post> result = postRepos.findByTitleContains(title);
        model.addAttribute("result", result);
        return "block-filter";
    }
    @PostMapping("/student/filter/result")
    public String studentResult(@RequestParam String surname, Model model){
        List<Student> result = studentRepos.findBysurnameContains(surname);
        model.addAttribute("result", result);
        return "student-filter";
    }
    @PostMapping("/prepod/filter/result")
    public String prepodResult(@RequestParam String surname, Model model){
        List<Prepod> result = prepodRepos.findBysurname(surname);
        model.addAttribute("result", result);
        return "prepod-filter";
    }

    @GetMapping("/prepod/{id}")
    public String prepodViewing(@PathVariable(value = "id") long id, Model model){
        Optional<Prepod> prepodOptional = prepodRepos.findById(id);
        ArrayList<Prepod> res = new ArrayList<>();
        prepodOptional.ifPresent(res::add);
        model.addAttribute("prepod",res);
        if(!prepodRepos.existsById(id)){
            return "redirect:/prepods";
        }
        return "prepod-detail";
    }
    @GetMapping("/prepod/{id}/edit")
    public String prepodEdit(@PathVariable("id") long id, Model model){
        Prepod res = prepodRepos.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id: "+id));
        model.addAttribute("prepod", res);
        return "prepod-edit";
    }
    @PostMapping("/prepod/{id}/edit")
    public String prepodUpdate(@PathVariable("id") long id, @ModelAttribute("prepod")@Validated Prepod prepod, BindingResult bindingResult){
        prepod.setId(id);
        if(bindingResult.hasErrors()){
            return "prepod-edit";
        }
        prepodRepos.save(prepod);
        return "redirect:/prepods";
    }

    @PostMapping("/prepod/{id}/remove")
    public String prepodDelete(@PathVariable("id")long id, Model model){
        Prepod prepod = prepodRepos.findById(id).orElseThrow();
        prepodRepos.delete(prepod);
        return "redirect:/prepod";
    }




    @GetMapping("/student/{id}")
    public String studentViewing(@PathVariable(value = "id") long id, Model model){
        Optional<Student> studentOptional = studentRepos.findById(id);
        ArrayList<Student> res = new ArrayList<>();
        studentOptional.ifPresent(res::add);
        model.addAttribute("student",res);
        if(!studentRepos.existsById(id)){
            return "redirect:/student";
        }
        return "student-detail";
    }
    @GetMapping("/student/{id}/edit")
    public String studentEdit(@PathVariable("id") long id, Model model){
        Student res = studentRepos.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id: "+id));
        model.addAttribute("student", res);
        return "student-edit";
    }
    @PostMapping("/student/{id}/edit")
    public String studentUpdate(@PathVariable("id") long id, @ModelAttribute("student") @Validated Student student, BindingResult bindingResult,
                                @RequestParam(value = "Birthday", defaultValue = "2000-01-01")@DateTimeFormat(pattern = "yyyy-MM-dd") Date Birthday){
        student.setId(id);
        student.setBirthday(Birthday);
        studentRepos.save(student);
        return "redirect:/student";
    }

    @PostMapping("/student/{id}/remove")
    public String studentDelete(@PathVariable("id")long id, Model model){
        Student student = studentRepos.findById(id).orElseThrow();
        studentRepos.delete(student);
        return "redirect:/student";
    }

    @GetMapping("/block/{id}")
    public String blockViewing(@PathVariable(value = "id") long id, Model model){
        Optional<Post> postOptional = postRepos.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        postOptional.ifPresent(res::add);
        model.addAttribute("post",res);
        if(!postRepos.existsById(id)){
            return "redirect:/block";
        }
        return "block-detail";
    }
    @GetMapping("/block/{id}/edit")
    public String blockEdit(@PathVariable("id") long id, Model model){
        Post res = postRepos.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный id: "+id));
        model.addAttribute("post", res);
        return "block-edit";
    }
    @PostMapping("/block/{id}/edit")
    public String blockUpdate(@PathVariable("id") long id, @ModelAttribute("post")@Validated Post post, BindingResult bindingResult){
        post.setId(id);
        if(bindingResult.hasErrors()){
            return "block-edit";
        }
        postRepos.save(post);
        return "redirect:/block";
    }

    @PostMapping("/block/{id}/remove")
    public String blockDelete(@PathVariable("id")long id, Model model){
        Post post = postRepos.findById(id).orElseThrow();
        postRepos.delete(post);
        return "redirect:/block";
    }

}
