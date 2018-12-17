package chess;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.King;
import chess.pieces.Rook;

public class PartidaDeXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		configuracaoInicial();
	}	
	
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] movPossivel(PosicaoXadrez origem) {
		Posicao posicao = origem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movPossivel();
	}
	
	public PecaDeXadrez perfomMovXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca capturarPeca = fazerMov(origem, destino);
		proxTurno();
		return (PecaDeXadrez) capturarPeca;
	}
	
	private Peca fazerMov(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new ExcecaoXadrez("Nao existe peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("A peca escolhida nao e sua");
		}
		if (!tabuleiro.peca(posicao).haUmMovPossivel()) {
			throw new ExcecaoXadrez("Nao existe movimentos possiveis para a peca escolhida.");
		}
	}
	
	private void validarPosicaoDestino (Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movPossivel(destino)) {
			throw new ExcecaoXadrez("A peca escolhida nao pode mover para a posicao de destino");
		}
	}
	
	private void proxTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
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
