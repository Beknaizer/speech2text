package com.example.demo.controllers;

import com.example.demo.IServices.ITranslateTextService;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.TranslateTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TranslateController {

    @Autowired
    private ITranslateTextService textTranslator;

    @GetMapping("/translate")
    @ResponseBody
    public String translate(@RequestParam(name="textToTranslate") String textToTranslate,
                            @RequestParam(name="fromLanguage") String fromLanguage,
                            @RequestParam(name="toLanguage") String toLanguage){

        String translatedText = textTranslator.translateText(textToTranslate,toLanguage,fromLanguage);

        System.out.println(translatedText);
        return translatedText;
    }


}
