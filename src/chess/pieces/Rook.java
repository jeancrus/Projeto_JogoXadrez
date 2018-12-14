package chess.pieces;

import boardgame.Tabuleiro;
import chess.Cor;
import chess.PecaDeXadrez;

public class Rook extends PecaDeXadrez{

	public Rook(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] movPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}
	
	
}
