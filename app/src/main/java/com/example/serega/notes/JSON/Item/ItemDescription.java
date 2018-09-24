package com.example.serega.notes.JSON.Item;

public class ItemDescription {
    private String description;

    public ItemDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public  String toString(){
        return description;
    }
}