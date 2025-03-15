package com.marysql.moneytrack;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Gasto {
    private String descricao;
    private double valor;
    private String data; // Formato "YYYY-MM-DD"

    // Construtor padrão necessário para a desserialização do Jackson
    public Gasto() {}

    public Gasto(@JsonProperty("descricao") String descricao,
                 @JsonProperty("valor") double valor,
                 @JsonProperty("data") String data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    // Getters e Setters
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
