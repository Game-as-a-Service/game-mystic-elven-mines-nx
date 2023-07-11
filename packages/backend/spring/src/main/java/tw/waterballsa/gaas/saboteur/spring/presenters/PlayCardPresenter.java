package tw.waterballsa.gaas.saboteur.spring.presenters;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterballsa.gaas.saboteur.app.usecases.PlayCardUsecase;
import tw.waterballsa.gaas.saboteur.domain.events.DestinationCardRevealedEvent;
import tw.waterballsa.gaas.saboteur.domain.events.DomainEvent;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class PlayCardPresenter implements PlayCardUsecase.Presenter {
    private DestinationCardRevealedViewModel viewModel;

    @SuppressWarnings("unchecked")
    private static <T extends DomainEvent> Optional<T> getEvent(List<DomainEvent> events,
                                                                Class<T> type) {
        return events.stream()
            .filter(e -> type.isAssignableFrom(e.getClass()))
            .map(e -> (T) e)
            .findFirst();
    }

    @Override
    public void present(List<DomainEvent> events) {
        viewModel = getEvent(events, DestinationCardRevealedEvent.class)
            .map(e -> new DestinationCardRevealedViewModel(e.isGold))
            .orElse(null);
    }

    public Optional<DestinationCardRevealedViewModel> getViewModel() {
        return ofNullable(viewModel);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DestinationCardRevealedViewModel {
        @JsonProperty("isGold")
        private boolean isGold;
    }
}
