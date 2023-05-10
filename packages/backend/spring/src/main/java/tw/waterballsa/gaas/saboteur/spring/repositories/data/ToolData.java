package tw.waterballsa.gaas.saboteur.spring.repositories.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.domain.Tool;
import tw.waterballsa.gaas.saboteur.domain.ToolName;

/**
 * @author johnny@waterballsa.tw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolData {
    protected ToolName toolName;
    protected boolean available;

    public Tool toDomain() {
        return new Tool(toolName, available);
    }

    public static ToolData toData(Tool tool) {
        return new ToolData(tool.getToolName(), tool.isAvailable());
    }
}
