package chess;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{
	private Cor cor;
	private int contarMov;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContarMov() {
		return contarMov;
	}
	
	public void aumentarContarMov() {
		contarMov++;
	}
	
	public void diminuirContarMov() {
		contarMov--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.dePosicao(posicao);
	}
	
	protected boolean haUmaPecaOponente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor; 
	}
}
