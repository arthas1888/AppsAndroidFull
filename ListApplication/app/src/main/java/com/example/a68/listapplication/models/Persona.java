package com.example.a68.listapplication.models;

import java.io.Serializable;


public class Persona implements Serializable {

    public String nombre;
    public String apellido;
    public int edad;

    public Persona(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }
}
