package application;

import chess.PartidaDeXadrez;

public class Program {

	public static void main(String[] args) {
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		UI.imprimirTabuleiro(partidaDeXadrez.getPecas());
		
	}

}
