package com.gaas.mystic.elven.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerCardPlacedMessage {
    String type;
    String playerName;
    CardMessage card;
}
