package br.com.wagnerandrade.springboot.domain;

public class Anime {

    private static final long serialVersionUID = -9177100189252636092L;

    private String name;

    public Anime() {
    }

    public Anime(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
