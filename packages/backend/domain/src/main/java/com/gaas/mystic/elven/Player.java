package com.gaas.mystic.elven;

import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.List.copyOf;

/**
 * @author johnny@waterballsa.tw
 */
@Builder
public class Player {
    protected String id;
    protected String name;

    @Singular
    protected List<Card> hands;

    protected Tool[] tools;

    public Player(String id, String name, List<Card> hands, Tool... tools) {
        this.id = id;
        this.name = name;
        this.hands = new ArrayList<>(hands);
        // TODO
        if (tools.length != 3) {
            throw new IllegalStateException("The number of tools should be 3");
        }

        long distinctTools = stream(tools).map(Tool::getToolName).distinct().count();
        if (distinctTools != 3) {
            throw new RuntimeException("The player must have mine cart and lantern and pick.");
        }
        this.tools = tools;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addHandCard(Card card) {
        hands.add(card);
    }

    public Tool getTool(ToolName toolName) {
        return stream(tools)
            .filter(tool -> tool.getToolName().equals(toolName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(format("Tool %s not found.", toolName)));
    }

    public Tool[] getTools() {
        return tools;
    }

    public List<Card> getHands() {
        return copyOf(hands);
    }

    public Card getHandCard(int handIndex) {
        return hands.get(handIndex);
    }

    public Card playCard(int handIndex) {
        return hands.remove(handIndex);
    }

    public boolean allToolsAreAvailable() {
        return stream(tools).allMatch(Tool::isAvailable);
    }

}
