package br.com.ucsal.olimpiadas;

public class Questao {

    private long id;
    private long provaId;
    private String enunciado;
    private String fenInicial;
    private String[] alternativas;
    private char alternativaCorreta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProvaId() {
        return provaId;
    }

    public void setProvaId(long provaId) {
        this.provaId = provaId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getFenInicial() {
        return fenInicial;
    }

    public void setFenInicial(String fenInicial) {
        this.fenInicial = fenInicial;
    }

    public String[] getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(String[] alternativas) {
        this.alternativas = alternativas;
    }

    public char getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(char alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }

    public boolean isRespostaCorreta(char marcada) {
        return alternativaCorreta == marcada;
    }

    public static char normalizar(char alternativa) {
        char valor = Character.toUpperCase(alternativa);
        if (valor < 'A' || valor > 'E') {
            throw new IllegalArgumentException("alternativa inválida");
        }
        return valor;
    }
}
