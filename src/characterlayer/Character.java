package characterlayer;

import opponentlayer.Opponent;

public interface Character<W>{

    public int punch(Opponent targetOpponent);

    public <W> int attackWithWeapon(W weapon, int option, Opponent targetOpponent);

    public void guard();

    public void run();

    public int specialAction(Opponent targetOpponent);

}