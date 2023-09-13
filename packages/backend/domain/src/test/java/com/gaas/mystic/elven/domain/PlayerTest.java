package com.gaas.mystic.elven.domain;

import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.domain.tool.ToolName;
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
                new Tool(ToolName.STARLIGHT_WAND, true),
                new Tool(ToolName.STARLIGHT_WAND, true),
                new Tool(ToolName.HARP_OF_HARMONY, true))
        );
    }
}
