package com.tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @Autowired
    BuildProperties buildProperties;

    @Value("${myparams.jdkversion}")
    String myjdkversion;

    @Value("${myparams.springbootversion}")
    String springbootversion;

    @Value("${application.name}")
    String applicationName;

    @Value("${build.version}")
    String buildVersion;

    @Value("${build.timestamp}")
    String buildTimestamp;

    @RequestMapping("/")
    public String showCustomerList(Model model) {
        String springVersion = SpringVersion.getVersion();

        String artifactApp = buildProperties.getArtifact();
        String versionApp = buildProperties.getVersion();

        model.addAttribute("myjdkversion", myjdkversion);
        model.addAttribute("springbooversion", springbootversion);
        model.addAttribute("springVersion", springVersion);
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("buildVersion", buildVersion);
        model.addAttribute("buildTimestamp", buildTimestamp);

        return "index";
    }   
}
