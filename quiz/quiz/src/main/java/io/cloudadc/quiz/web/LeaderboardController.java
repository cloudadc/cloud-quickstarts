package io.cloudadc.quiz.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.cloudadc.quiz.model.LeaderBoardEntry;
import io.cloudadc.quiz.services.gcp.spanner.SpannerService;

@Controller
public class LeaderboardController {
	
	@Autowired
    private SpannerService spannerService;
	
	@GetMapping("/leaderboard")
    public String getLeaderboard(Model model){
		
        List<LeaderBoardEntry> leaderBoardEntries = spannerService.getQuizLeaders();
        model.addAttribute("leaders", leaderBoardEntries);
        return "leaderboard";
    }

}
