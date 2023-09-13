package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.Player;
import com.gaas.mystic.elven.Tool;
import com.gaas.mystic.elven.ToolName;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    @Test
    void playerMustOwn3Tools() {
        assertThrows(RuntimeException.class,
            () -> new Player("id", "id", emptyList(), new Tool[0])
        );
    }

    @Test
    void playerMustHaveMineCartLanternPick() {
        assertThrows(RuntimeException.class,
            () -> new Player("id", "id",
                emptyList(),
                new Tool(ToolName.PICK, true),
                new Tool(ToolName.PICK, true),
                new Tool(ToolName.LANTERN, true))
        );
    }
}
