package com.gaas.mystic.elven.presenters;

import com.gaas.mystic.elven.domain.role.Player;
import com.gaas.mystic.elven.domain.tool.Tool;
import com.gaas.mystic.elven.presenters.views.CardView;
import com.gaas.mystic.elven.usecases.FindPlayerUsecase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class FindPlayerPresenter implements FindPlayerUsecase.Presenter {

    private FindPlayerViewModel viewModel;

    @Override
    public void renderPlayer(Player player) {
        viewModel = FindPlayerViewModel.builder()
            .playerId(player.getId())
            .playerName(player.getName())
            .cardNum(player.getHands().size())
            .tools(Arrays.asList(player.getTools()))
            .role(player.getRoleCard().name())
            .cards(player.getHands().stream().map(CardView::toView).toList())
            .build();
    }

    public FindPlayerViewModel present() {
        return viewModel;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindPlayerViewModel {
        private String playerId;
        private String playerName;
        private Integer cardNum;
        private List<Tool> tools;
        private String role;
        private List<CardView> cards;
    }

}
