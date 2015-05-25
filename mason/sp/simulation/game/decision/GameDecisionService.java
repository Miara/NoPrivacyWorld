package sp.simulation.game.decision;

import sp.simulation.PersonAgent;
import sp.simulation.game.Game;

public interface GameDecisionService {
	public boolean ifPlayGame(PersonAgent personAgent, Game game, double trust);
}
