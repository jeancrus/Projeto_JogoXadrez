package application;

import java.util.Scanner;

import chess.PartidaDeXadrez;
import chess.PecaDeXadrez;
import chess.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

		while (true) {
			UI.imprimirTabuleiro(partidaDeXadrez.getPecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
			
			System.out.println();
			System.out.print("Target: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
			
			PecaDeXadrez pecaCapturada = partidaDeXadrez.perfomMovXadrez(origem, destino);
		}
	}

}
