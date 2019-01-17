package br.ce.wendt.learn.leilaotest.exception;

import java.util.ArrayList;
import java.util.List;

public class Leilao {

    private String descricao;
    private List<Lance> lances = new ArrayList<>();

    public Leilao(String descricao) {

    }

    /**
     * Registra novo lance no leilão
     */

    public void darLance(Lance lance){
        if(lance.getValor() < 0){
            throw new RuntimeException("Lance com valor negativo");
        }

        this.lances.add(lance);
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Lance> getLances() {
        return lances;
    }

    public void setLances(List<Lance> lances) {
        this.lances = lances;
    }
}
