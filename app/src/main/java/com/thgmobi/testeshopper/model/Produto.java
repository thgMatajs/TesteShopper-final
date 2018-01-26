package com.thgmobi.testeshopper.model;

import java.io.Serializable;

/**
 * Created by usuario on 25/01/2018.
 */

public class Produto implements Serializable {

    private int id;
    private String nome;
    private String preco;
    private String codigoBarra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
