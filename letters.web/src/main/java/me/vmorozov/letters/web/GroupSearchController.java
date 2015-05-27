package me.vmorozov.letters.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author VOVA
 */
@Controller
public class GroupSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupSearchController.class);

    @RequestMapping("/groups")
    public String groups() {
        LOGGER.debug("in controller");
        return "groups";
    }
}
