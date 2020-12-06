package br.com.wagnerandrade.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Anime {

    private static final long serialVersionUID = -9177100189252636092L;

    private Long id;
    private String name;

}
