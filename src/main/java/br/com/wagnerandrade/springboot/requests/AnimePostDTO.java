package br.com.wagnerandrade.springboot.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnimePostDTO {
    @NotEmpty(message = "the anime name cannot be empty")
    private String name;
}
