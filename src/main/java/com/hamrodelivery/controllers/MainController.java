package com.hamrodelivery.controllers;

import com.hamrodelivery.models.Product;
import com.hamrodelivery.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

//    We are going to perform CRUD operations on '/' route

/*
    @PostMapping
    @PutMapping
    @GetMapping
    @PatchMapping
    @DeleteMapping
*/

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index.html");
        mv.addObject("products",productRepository.findAll());
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addProd(){
        ModelAndView mv = new ModelAndView("add_prod.html");
        return mv;
    }

    @PostMapping("/add")
    public void addProduct(HttpServletResponse response,
                           @RequestParam("heading") String head,
                           @RequestParam("brand") String brand,
                           @RequestParam("rating") float rating,
                           @RequestParam("a_m") float acAm,
                           @RequestParam("d_m") float disAm
                           ) throws IOException {

        try{
            Product product = new Product();
            product.setHeading(head);
            product.setBrand(brand);
            product.setRatings(rating);
            product.setActualAmnt(acAm);
            product.setDisAmnt(disAm);

            productRepository.save(product);

            response.sendRedirect("/");
        }catch (Exception e){
            throw
                    new RuntimeException("Product object cannot be created");
        }

    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProd(@PathVariable("id") int id){
        ModelAndView mv  = new ModelAndView("edit_p.html");
        mv.addObject("obj",productRepository.findById(id).get());
        return mv;
    }

    @PostMapping("/edit/{id}")
    public void editProduct(HttpServletResponse response,
                           @PathVariable("id") int id,
                           @RequestParam("heading") String head,
                           @RequestParam("brand") String brand,
                           @RequestParam("rating") float rating,
                           @RequestParam("a_m") float acAm,
                           @RequestParam("d_m") float disAm
    ) throws IOException {

        try{
            Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: "+id));
            product.setHeading(head);
            product.setBrand(brand);
            product.setRatings(rating);
            product.setActualAmnt(acAm);
            product.setDisAmnt(disAm);

            productRepository.save(product);

            response.sendRedirect("/");
        }catch (Exception e){
            throw
                    new RuntimeException("Product object cannot be created");
        }

    }

    @GetMapping("/delete/{id}")
    public void DeleteProd(HttpServletResponse response, @PathVariable("id") int id) throws IOException {
        try {
            productRepository.deleteById(id);
            response.sendRedirect("/");
        }catch (Exception e){
            throw
                    new RuntimeException("Error in deleting object with id: "+id);
        }
    }

}
