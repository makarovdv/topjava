package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class StopwatchImpl extends Stopwatch {

    private static final Logger log = getLogger(StopwatchImpl.class);

    private static Map<Class,List<String>> allTests = new HashMap<>();

    private void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String testInfo = String.format("Test %s %s, spent %d ms",
                testName, status, TimeUnit.NANOSECONDS.toMillis(nanos));
        addToSummary(testInfo);
        log.info(testInfo);
    }

    private void addToSummary(String testinfo){
        List<String> tests = allTests.get(testingClass);
        if (tests == null) {
            tests = new ArrayList<>();
            allTests.put(testingClass,tests);
        }
        tests.add(testinfo);
    }

    private Class testingClass;

    public StopwatchImpl(Class testingClass) {
        this.testingClass = testingClass;
    }

    public static void logResult(Class testingClass){
        log.info("Summary for {}:",testingClass.getName());
        log.info(getSummary(testingClass));
    }

    private static String getSummary(Class testingClass){
        System.out.println(allTests.get(testingClass));
        return allTests.get(testingClass)
                .stream()
                .collect(Collectors.joining("\n","\n",""));
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }
};