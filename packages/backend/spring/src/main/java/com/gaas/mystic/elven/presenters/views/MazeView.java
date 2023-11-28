package com.gaas.mystic.elven.presenters.views;

import com.gaas.mystic.elven.domain.ElvenGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MazeView {
    private List<PathCardView> pathCards;
    private List<GoalCardView> goalCards;

    public static MazeView toView(ElvenGame game) {
        return MazeView.builder()
            .pathCards(game.getMaze().getPaths().stream().map(PathCardView::toView).toList())
            .goalCards(game.getDestinations().stream().map(GoalCardView::toView).toList())
            .build();
    }
}
