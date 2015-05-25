package sp.simulation.game.creation;

import sp.simulation.PersonAgent;
import sp.simulation.game.GamePair;
import ec.util.MersenneTwisterFast;

public interface GameCreationService {
	public GamePair createGame(PersonAgent personAgent, MersenneTwisterFast random);
}
