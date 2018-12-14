package chess;

import boardgame.Tabuleiro;
import chess.pieces.King;
import chess.pieces.Rook;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}

	private void configuracaoInicial() {
		colocarNovaPeca('c', 1, new Rook(tabuleiro, Cor.WHITE));
		colocarNovaPeca('c', 2, new Rook(tabuleiro, Cor.WHITE));
		colocarNovaPeca('d', 2, new Rook(tabuleiro, Cor.WHITE));
		colocarNovaPeca('e', 2, new Rook(tabuleiro, Cor.WHITE));
		colocarNovaPeca('e', 1, new Rook(tabuleiro, Cor.WHITE));
		colocarNovaPeca('d', 1, new King(tabuleiro, Cor.WHITE));
		
		
		colocarNovaPeca('c', 7, new Rook(tabuleiro, Cor.BLACK));
		colocarNovaPeca('c', 8, new Rook(tabuleiro, Cor.BLACK));
		colocarNovaPeca('d', 7, new Rook(tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 7, new Rook(tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 8, new Rook(tabuleiro, Cor.BLACK));
		colocarNovaPeca('d', 8, new King(tabuleiro, Cor.BLACK));
	}
}
