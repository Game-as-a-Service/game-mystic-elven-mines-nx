package com.gaas.mystic.elven.repositories.data;

import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.domain.tool.ToolName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
