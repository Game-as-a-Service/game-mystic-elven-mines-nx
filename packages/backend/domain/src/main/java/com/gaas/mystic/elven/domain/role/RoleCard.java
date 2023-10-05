package com.gaas.mystic.elven.domain.role;

/**
 * 角色卡，每個玩家都會分配到一個
 * 精靈 Elven
 * 哥布林 Goblin
 */
public enum RoleCard {
    ELVEN, GOBLIN;

    public static RoleCard from(String name) {
        if (name == null) {
            return null;
        }
        return valueOf(name);
    }
}
