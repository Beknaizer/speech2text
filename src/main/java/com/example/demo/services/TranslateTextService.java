package com.example.demo.services;

import com.example.demo.IServices.ITranslateTextService;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Component;


@Component
public class TranslateTextService implements ITranslateTextService {

    @Override
    public String translateText(String textToTranslate, String toLanguage, String fromLanguage) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation =
                translate.translate(
                        textToTranslate,
                        Translate.TranslateOption.sourceLanguage(fromLanguage),
                        Translate.TranslateOption.targetLanguage(toLanguage),
                        // Use "base" for standard edition, "nmt" for the premium model.
                        Translate.TranslateOption.model("base"));
        return translation.getTranslatedText();
    }
}
