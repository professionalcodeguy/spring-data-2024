package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import application.repository.LivroRepository;
import org.springframework.ui.Model;

import application.model.Genero;
import application.model.Livro;
import application.repository.GeneroRepository;

@Controller
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroRepository livrosRepo;
    @Autowired
    private GeneroRepository generoRepo;
    
    @RequestMapping("/list")
    public String list(Model ui){
        ui.addAttribute("livros", livrosRepo.findAll());
        return "/livro/list";
    }

    @RequestMapping("/insert")
    public String insert(Model ui) {
        ui.addAttribute("generos", generoRepo.findAll());
        return "/livro/insert";
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestParam("titulo") String titulo, @RequestParam("genero") long generoId) {
        Optional<Genero> resultado = generoRepo.findById(generoId);
        if(resultado.isPresent()){
            Livro livro = new Livro();
            livro.setTitulo(titulo);
            livro.setGenero(resultado.get());

            livrosRepo.save(livro);
        }
        return "redirect:/livros/list";
    }
}
