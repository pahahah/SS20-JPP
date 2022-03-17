package jpp.gametheory.generic;

import java.util.Map;
import java.util.Set;

/**
 * Eine Klasse, die dieses Interface implementiert, repräsentiert eine Runde eines bestimmten Spiels
 * und speichert die Züge, die die einzelnen Spieler gemacht haben.
 *
 */
public interface IGameRound<C extends IChoice> {

    /**
     * Gibt die Zuordnung Spieler -> Entscheidung/Zug in dieser Runde zurück.
     * Achten sie darauf dass von außerhalb keine Änderungen an der Klasse vorgenommen werden können.
     * @return Zuordnung der Spieler auf ihre Sterategien.
     */
    Map<IPlayer<C>, C> getPlayerChoices();

    /**
     * Gibt den Zug eines Spielers in dieser Runde zurück.
     Falls ##player## in der Runde nicht mitgespielt hat, soll eine aussagekräftige ##IllegalArgumentException## geworfen werden.
     * @param player Spieler für den der Zug in dieser Runde ausgegeben werden soll.
     * @return Zug des Spielers
     * @throws IllegalArgumentException falls der Spieler in der Runde nich mitgespielt hat.
     */
    C getChoice(IPlayer<C> player);

    /**
     * Gibt ein Set aller Spieler in dieser Runde zurück.
     * @return Set der Spieler dieser Runde
     */
    Set<IPlayer<C>> getPlayers();

    /**
     * Gibt zu einem Spieler alle anderen Spieler dieser Runde zurück.
     * @param player Spieler für den die anderen Spieler gesucht werden.
     * @return Set aller andern Spieler.
     */
    Set<IPlayer<C>> getOtherPlayers(IPlayer<C> player);

}
