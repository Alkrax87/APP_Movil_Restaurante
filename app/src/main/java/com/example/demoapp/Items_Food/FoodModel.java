package com.example.demoapp.Items_Food;

public class FoodModel {

    String titulo, descripcion, precio, img;

    public FoodModel() {
    }

    public FoodModel(String titulo, String descripcion, String precio, String img) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}


