package com.example.serega.notes.JSON.Item;

public class Item {
    private long id;
    private String name;
    private String description;
    private String color;

    public Item(long id, String name, String description, String color){
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    @Override
    public  String toString(){
        return name;
    }
}
