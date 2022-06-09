package com.example.demoapp.Items_Orders;

public class OrderModel {

    String titulo,precio,user,time,img;

    public OrderModel() {
    }

    public OrderModel(String titulo, String precio, String user, String time, String img) {
        this.titulo = titulo;
        this.precio = precio;
        this.user = user;
        this.time = time;
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
