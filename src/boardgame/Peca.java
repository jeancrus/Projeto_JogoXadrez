package boardgame;

public class Peca {
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	public Peca(Tabuleiro tabuleiro) {
		// somente tabuleiro pois a posi��o come�ar� nula
		this.tabuleiro = tabuleiro;
		posicao = null;
	}
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	
}
