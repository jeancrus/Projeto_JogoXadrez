package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class PartidaDeXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if(testeCheque(jogadorAtual)) {
			desfazerMov(origem, destino, capturarPeca);
			throw new ExcecaoXadrez("Voce nao pode se colocar em cheque");
		}
		
		check = (testeCheque(oponente(jogadorAtual))) ? true : false;
		
		if (testChequeMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proxTurno();
		}
		return (PecaDeXadrez) capturarPeca;
	}
	
	private Peca fazerMov(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(origem);
		p.aumentarContarMov();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMov(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
		p.diminuirContarMov();
		tabuleiro.colocarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
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
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private PecaDeXadrez king(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof King) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe o rei " + cor + " no tabuleiro");
	}
	
	private boolean testeCheque(Cor cor) {
		Posicao posicaoKing = king(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecaOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecaOponente) {
			boolean[][] mat = p.movPossivel();
			if (mat[posicaoKing.getLinha()][posicaoKing.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testChequeMate(Cor cor) {
		if (!testeCheque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movPossivel();
			for (int i = 0;i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMov(origem, destino);
						boolean testarCheque = testeCheque(cor);
						desfazerMov(origem, destino, pecaCapturada);
						if(!testarCheque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void configuracaoInicial() {
		colocarNovaPeca('a', 1, new Rook(tabuleiro, Cor.WHITE));
		colocarNovaPeca('b', 1, new Knight(tabuleiro, Cor.WHITE));
		colocarNovaPeca('c', 1, new Bishop(tabuleiro, Cor.WHITE));
		colocarNovaPeca('e', 1, new King(tabuleiro, Cor.WHITE));
		colocarNovaPeca('f', 1, new Bishop(tabuleiro, Cor.WHITE));
		colocarNovaPeca('g', 1, new Knight(tabuleiro, Cor.WHITE));
		colocarNovaPeca('h', 1, new Rook(tabuleiro, Cor.WHITE));
		colocarNovaPeca('a', 2, new Pawn(tabuleiro, Cor.WHITE));
		colocarNovaPeca('b', 2, new Pawn(tabuleiro, Cor.WHITE));
		colocarNovaPeca('c', 2, new Pawn(tabuleiro, Cor.WHITE));
		colocarNovaPeca('d', 2, new Pawn(tabuleiro, Cor.WHITE));
		colocarNovaPeca('e', 2, new Pawn(tabuleiro, Cor.WHITE));
		colocarNovaPeca('f', 2, new Pawn(tabuleiro, Cor.WHITE));
		colocarNovaPeca('g', 2, new Pawn(tabuleiro, Cor.WHITE));
		colocarNovaPeca('h', 2, new Pawn(tabuleiro, Cor.WHITE));
		
		colocarNovaPeca('a', 8, new Rook(tabuleiro, Cor.BLACK));
		colocarNovaPeca('b', 8, new Knight(tabuleiro, Cor.BLACK));
		colocarNovaPeca('c', 8, new Bishop(tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 8, new King(tabuleiro, Cor.BLACK));
		colocarNovaPeca('f', 8, new Bishop(tabuleiro, Cor.BLACK));
		colocarNovaPeca('g', 8, new Knight(tabuleiro, Cor.BLACK));
		colocarNovaPeca('h', 8, new Rook(tabuleiro, Cor.BLACK));
		colocarNovaPeca('a', 7, new Pawn(tabuleiro, Cor.BLACK));
		colocarNovaPeca('b', 7, new Pawn(tabuleiro, Cor.BLACK));
		colocarNovaPeca('c', 7, new Pawn(tabuleiro, Cor.BLACK));
		colocarNovaPeca('d', 7, new Pawn(tabuleiro, Cor.BLACK));
		colocarNovaPeca('e', 7, new Pawn(tabuleiro, Cor.BLACK));
		colocarNovaPeca('f', 7, new Pawn(tabuleiro, Cor.BLACK));
		colocarNovaPeca('g', 7, new Pawn(tabuleiro, Cor.BLACK));
		colocarNovaPeca('h', 7, new Pawn(tabuleiro, Cor.BLACK));
	}
}
