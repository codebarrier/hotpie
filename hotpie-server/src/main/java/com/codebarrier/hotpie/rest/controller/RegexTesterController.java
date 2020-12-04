package com.codebarrier.hotpie.rest.controller;

import com.codebarrier.hotpie.model.RegexTestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RestController
public class RegexTesterController {

    @PostMapping("/testRegex")
    public RegexTestResult testRegex(@RequestParam("regex") String regex, @RequestParam("sampleData") String sampleData) {
        RegexTestResult result = new RegexTestResult();
        try {
            Pattern regexPattern = Pattern.compile(regex);
            Matcher filterMatcher = regexPattern.matcher(sampleData);
            result.setSampleData(sampleData);
            result.setRegex(regex);
            result.setMatchSuccessful(false);
            if (filterMatcher.matches()) {
                result.setMatchSuccessful(true);
                List<String> matchedData = new ArrayList<>();
                if (filterMatcher.groupCount() > 0) {
                    for (int grpCount = 1; grpCount <= filterMatcher.groupCount(); grpCount++) {
                        matchedData.add(filterMatcher.group(grpCount));
                    }
                }
                result.setMatchedGroups(matchedData);
            }
            return result;
        } catch (PatternSyntaxException exception) {
            result.setMatchSuccessful(false);
            return result;
        }
    }
}
