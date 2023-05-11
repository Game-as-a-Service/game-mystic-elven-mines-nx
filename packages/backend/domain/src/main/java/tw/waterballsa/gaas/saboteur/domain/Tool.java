package tw.waterballsa.gaas.saboteur.domain;

/**
 * @author johnny@waterballsa.tw
 */
public class Tool {
    // 遊戲會瘋狂出現的新的 tool 來逼你擴充嗎
    // 擴充時很累嗎？
    protected final ToolName toolName;
    protected boolean available;

    public Tool(ToolName toolName, boolean available) {
        this.toolName = toolName;
        this.available = available;
    }

    public ToolName getToolName() {
        return toolName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
