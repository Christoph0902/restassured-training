package org.example.model;

public class NewProjectPayload {

    private String name;

    public NewProjectPayload(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
