package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ExcecaoXadrez;
import chess.PartidaDeXadrez;
import chess.PecaDeXadrez;
import chess.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

		while (true) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaDeXadrez);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movPossivel = partidaDeXadrez.movPossivel(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partidaDeXadrez.getPecas(), movPossivel);
				System.out.println();
				System.out.print("Target: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaDeXadrez pecaCapturada = partidaDeXadrez.perfomMovXadrez(origem, destino);
			} catch(ExcecaoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
