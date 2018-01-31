package SocialNetworkAnalysis;

import java.util.HashMap;
import java.util.Map;

public class SyncSolver {

    private Map<String, UserNode> map = new HashMap<>();

    public void solve(Map<String, UserNode> map) {
        this.map = map;
        calculateSync();
        calculateNorm();
    }

    private void calculateSync() {

    }

    private void calculateNorm() {

    }
}
