package com.example.demoapp.Items_Orders_List;

public class OrderListModel {

    String titulo,precio,user,time,img,cliente;

    public OrderListModel() {
    }

    public OrderListModel(String titulo, String precio, String user, String time, String img, String cliente) {
        this.titulo = titulo;
        this.precio = precio;
        this.user = user;
        this.time = time;
        this.img = img;
        this.cliente = cliente;
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
