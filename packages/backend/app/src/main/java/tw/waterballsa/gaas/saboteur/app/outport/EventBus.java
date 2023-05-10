package tw.waterballsa.gaas.saboteur.app.outport;

import tw.waterballsa.gaas.saboteur.domain.events.DomainEvent;

import java.util.List;

/**
 * @author johnny@waterballsa.tw
 */
public interface EventBus {
    void broadcast(List<DomainEvent> events);
}
