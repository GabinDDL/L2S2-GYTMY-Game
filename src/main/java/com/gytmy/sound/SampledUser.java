package com.gytmy.sound;

import java.io.File;
import java.util.HashMap;

public class SampledUser {

    private static String PATH = "src/resources/audioFiles/";

    private String name;
    private HashMap<String, Integer> samples;
    private File yamlConfig = new File(PATH + "/" + name + "/" + "data.yaml");

    public SampledUser(String name) {
        this.name = name;
        this.samples = new HashMap<String, Integer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getSamples() {
        return samples;
    }

    public void setSamples(HashMap<String, Integer> samples) {
        this.samples = samples;
    }

    public File getYamlConfig() {
        return yamlConfig;
    }
}
