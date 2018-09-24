package com.example.serega.notes.JSON.Item;

public class ItemName {
    private String name;

    public ItemName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public  String toString(){
        return name;
    }
}
