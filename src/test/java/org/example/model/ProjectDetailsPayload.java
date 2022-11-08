package org.example.model;

public class ProjectDetailsPayload {

    private String id;
    private String parent_id;
    private int order;
    private String color;
    private String name;
    private int comment_count;
    private boolean is_shared;
    private boolean is_favorite;
    private Boolean is_inbox_project;
    private Boolean is_team_inbox;
    private String url;
    private String view_style;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public boolean isIs_shared() {
        return is_shared;
    }

    public void setIs_shared(boolean is_shared) {
        this.is_shared = is_shared;
    }

    public boolean isIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public Boolean getIs_inbox_project() {
        return is_inbox_project;
    }

    public void setIs_inbox_project(Boolean is_inbox_project) {
        this.is_inbox_project = is_inbox_project;
    }

    public Boolean getIs_team_inbox() {
        return is_team_inbox;
    }

    public void setIs_team_inbox(Boolean is_team_inbox) {
        this.is_team_inbox = is_team_inbox;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getView_style() {
        return view_style;
    }

    public void setView_style(String view_style) {
        this.view_style = view_style;
    }
}
