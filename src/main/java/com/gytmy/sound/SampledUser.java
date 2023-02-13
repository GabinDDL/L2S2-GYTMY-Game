package com.gytmy.sound;

import java.io.File;

public class SampledUser extends User {

    private static String PATH = "src/resources/audioFiles/";

    private File yamlConfig = new File(PATH + "/" + getFirstname() + "/" + "data.yaml");

    public SampledUser(String firstname, String lastname, int numEtu) {
        super(firstname, lastname, numEtu);
    }

    public SampledUser(String name, int numEtu) {
        super(name, "default", numEtu);
    }

    public SampledUser(String name) {
        super(name, "default", 0);
    }

    public File getYamlConfig() {
        return yamlConfig;
    }
}
