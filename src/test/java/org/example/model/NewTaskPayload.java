package org.example.model;

public class NewTaskPayload {

    private String content;
    private String project_id;

    public NewTaskPayload(String content) {
        this.content = content;
    }

    public NewTaskPayload(String content, String project_id) {
        this.content = content;
        this.project_id = project_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String name) {
        this.content = content;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
}
