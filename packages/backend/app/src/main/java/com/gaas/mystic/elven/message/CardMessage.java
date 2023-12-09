package com.gaas.mystic.elven.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardMessage {
    private Integer row;
    private Integer col;
    // card name
    private String name;
    // for map card
    private Boolean isGoal;
    private Boolean flipped;
    // for fix, broken card
    private String targetPlayerName;
}
