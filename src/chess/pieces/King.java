package chess.pieces;

import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaDeXadrez;

public class King extends PecaDeXadrez {

	public King(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] movPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}

	
}
