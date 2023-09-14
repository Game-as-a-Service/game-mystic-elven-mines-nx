package com.gaas.mystic.elven.domain.tool;

import lombok.Getter;

/**
 * 道具
 */
@Getter
public class Tool {
    // 遊戲會瘋狂出現的新的 tool 來逼你擴充嗎
    // 擴充時很累嗎？
    protected final ToolName toolName;
    protected boolean available;

    public Tool(ToolName toolName, boolean available) {
        this.toolName = toolName;
        this.available = available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
