package com.huertohogar.huerto_backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT") // coincide con H2 (mayúsculas da igual, H2 no distingue)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false)
    private String caracteristicas; // "organico,facil-cultivo"

    // 🔹 Constructor vacío requerido por JPA
    public Product() {
    }

    // 🔹 (Opcional) constructor con campos (sin id)
    public Product(String nombre, Integer precio, String categoria, Boolean disponible,
                   Integer stock, String descripcion, String imagen, String caracteristicas) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.disponible = disponible;
        this.stock = stock;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.caracteristicas = caracteristicas;
    }

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}
