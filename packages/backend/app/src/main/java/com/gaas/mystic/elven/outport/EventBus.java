package com.gaas.mystic.elven.outport;

import com.gaas.mystic.elven.events.DomainEvent;

import java.util.List;

/**
 * @author johnny@waterballsa.tw
 */
public interface EventBus {
    void broadcast(List<DomainEvent> events);
}
