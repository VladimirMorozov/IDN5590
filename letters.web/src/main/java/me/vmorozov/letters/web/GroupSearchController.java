package me.vmorozov.letters.web;

import me.vmorozov.letters.Letters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VOVA
 */
@Controller
@RequestMapping("/groups")
public class GroupSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupSearchController.class);

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showInputForm() {
        LOGGER.debug("showing form");
        return "groups";
    }

    @RequestMapping("/result")
    public void find(boolean fromBeginning,
                     boolean useCustomAlphabet,
                     boolean caseSensitive,
                     boolean sort,
                     String customAlphabet,
                     String text,

                     HttpServletResponse response) {

        LOGGER.debug("params: fromBeginning:" + fromBeginning +
                ", useCustomAlphabet: " + useCustomAlphabet +
                ", customAlphabet: " + customAlphabet +
                ", text: " + text);

        List<Character> alphabet;
        if (useCustomAlphabet == false) {
            alphabet = Letters.ESTONIAN_ALPHABET;
        } else {
            alphabet = new ArrayList<Character>();
            for (Character letter : customAlphabet.toUpperCase().toCharArray()) {
                alphabet.add(letter);
            }
        }

        Letters letters = new Letters();
        LinkedHashMap<String, Integer> result = letters.getCharacterGroupMap(text, alphabet, caseSensitive, sort, fromBeginning);

        String output = "";
        for( Map.Entry<String, Integer> subWord : result.entrySet()) {
            output += subWord.getKey() + " " + subWord.getValue() + "\n";
        }

        try {
            response.setHeader("Content-disposition", "attachment; filename=output.txt");
            response.setContentType("text/html; charset=UTF-8");
            response.getOutputStream().write(output.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
