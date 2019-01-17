package br.ce.wendt.learn.leilaotest.exception;

public class Lance {

    private String nome;
    private double valor;

    public Lance(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Lance() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
