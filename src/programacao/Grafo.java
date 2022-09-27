package programacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Scanner;

public class Grafo {
	
	public static void main(String args[]) throws IOException, ParseException, InterruptedException {
		
		String path = "src\\\\arquivoTxt\\\\pequenoG.txt";
		String buffRead = " ";
		buffRead = new String(Files.readAllBytes(Paths.get(path)));
		String buff = buffRead.replace("\n", "\s");      
		String[] arrayNovidade = buff.split(" ");
		int[] arrayNovidadeInt;
			
		//transformando o array de string em int
		arrayNovidadeInt = TransformarArrayInt(arrayNovidade);
		
		int tamanhoGrafo = arrayNovidadeInt[0], ordemGrafo = arrayNovidadeInt[1];
		
		//inicializando a matriz adjacente
		int[][] matrixAdjacente = new int[arrayNovidadeInt[0]][arrayNovidadeInt[1]];
		
		//atribuindo dados a matriz
		matrixAdjacente = atribuirDadosMatriz(matrixAdjacente, arrayNovidadeInt);
		
		
		//Mensagens solicitadas
		System.out.println();
		System.out.println("Ordem: " + ordemGrafo);
		System.out.println("Grafo: " + tamanhoGrafo);
		int [] grauEntrada = grausDeEntradaDeCadaVertice(matrixAdjacente, tamanhoGrafo);
		int [] grauSaida = grausDeSaidaDeCadaVertice(matrixAdjacente, tamanhoGrafo );
        
        analisarVeticeIsolado(grauEntrada, grauSaida);


        
        
		// distancia de cada vertices - > atividade 2:
		Scanner leitor = new Scanner(System.in);
		System.out.println();
		System.out.println();
		System.out.println("Informe o vertice fonte:");
		int vFonte = leitor.nextInt();
		System.out.println("Informe o vertice final:");
		int vFinal = leitor.nextInt();

		analisarDistancia(matrixAdjacente, vFonte, vFinal);
		
		// profundidade -> atividade 3:
		
		System.out.println("#CALCULO DE PROFUNDIDADE:");
		System.out.println();
		System.out.println();
		System.out.println("Informe o vertice:");
		int vProfund = leitor.nextInt();
		
		analisarProfundidade(matrixAdjacente, vProfund);
		
	}
	


	private static void analisarProfundidade(int[][] matrixAdjacente, int vProfund) {
		int tempo;
		EnumCor []cor = new EnumCor[matrixAdjacente.length];
		Integer []ante = new Integer[matrixAdjacente.length];
		long []distancia = new long[matrixAdjacente[0].length];
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		
		for(int i = 0; i < distancia.length; i++) {
			cor[i] = EnumCor.BRANCO;
			ante[i] = -1; 
		}
		
		tempo=1;
		
		int []tempoInicial = new int[13];
		int []tempoFinal = new int[13];
		
			analisarVisitante(matrixAdjacente, vProfund, tempo, cor, linkedList, tempoInicial, tempoFinal, distancia, ante);
		
		
		}
	
	
	private static void analisarVisitante(int[][] matrixAdjacente, int vProfund, int tempo, EnumCor[] cor, LinkedList<Integer> linkedList, int[] tempoInicial, int[] tempoFinal, long[] distancia, Integer[] ante) {
	
		cor[vProfund] = EnumCor.CINZA;
		tempoInicial[vProfund] = tempo++;
		System.out.println(" "  + vProfund );
		
		for (int i = 0; i < matrixAdjacente.length; i++) {
			if(matrixAdjacente[i][vProfund] == 1 || matrixAdjacente[vProfund][i] == 1){
				if(cor[i]== EnumCor.BRANCO){
					cor[i] = EnumCor.CINZA;
					ante[i] = vProfund;
					analisarVisitante(matrixAdjacente, i, tempo, cor, linkedList, tempoInicial, tempoFinal, distancia, ante);
				}
			}
		}
		
		cor[vProfund]= EnumCor.PRETO;
		tempoFinal[vProfund]= tempo++;	
		
	}



	private static void imprimir(Integer v, int verticeFonte, Integer []ante){
		
		if(v == verticeFonte){
			System.out.print(verticeFonte + " -> ");	
		} else {
			if(ante[v] == null){
				System.out.print("Não ha caminho");
			} else {
				imprimir(ante[v], verticeFonte, ante);
				System.out.print(v + "  ");
			}
		}
	}

	private static void analisarDistancia(int[][] matrixAdjacente, int verticeFonte, int v){
		EnumCor []cor = new EnumCor[matrixAdjacente.length];
		Integer []ante = new Integer[matrixAdjacente.length];
		long []distancia = new long[matrixAdjacente[0].length];
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		
		for (int i = 0; i < distancia.length; i++) {
			cor[i] = EnumCor.BRANCO;
			distancia[i] = 9999999999999L;
			ante[i] = null; 
		}

		cor[verticeFonte] = EnumCor.CINZA;
		distancia[verticeFonte] = 0;

		linkedList.add(verticeFonte);

		while(!linkedList.isEmpty()){
			Integer u = linkedList.pop();

			for (int i = 0; i < matrixAdjacente.length; i++) {
				if(matrixAdjacente[i][u] == 1 || matrixAdjacente[u][i] == 1){
					if(cor[i]== EnumCor.BRANCO){
						cor[i] = EnumCor.CINZA;
						distancia[i] = distancia[u] + 1;
						ante[i] = u;
						linkedList.add(i);
					}
				}
			}
			cor[u]= EnumCor.PRETO;
		}
		imprimir(v, verticeFonte, ante);
	}
	
	private static int[] grausDeEntradaDeCadaVertice(int[][] matrixAdjacente, int tamanhoGrafo) {
		int[] grauEntrada = new int [tamanhoGrafo];
		int grau = 0;
		
		for(int cont=0; cont<matrixAdjacente.length; cont ++) {
			for(int cont2=0; cont2<matrixAdjacente.length; cont2 ++) {
				
				if(matrixAdjacente[cont2][cont] == 1) {
					grau++;
				}
			}
			grauEntrada[cont]=grau;
			grau = 0;
			
		}
		System.out.println();
		System.out.println();
		System.out.print("Grau de entrada:  ");
		for(int cont=0; cont<tamanhoGrafo; cont++) {
			if(cont<tamanhoGrafo) {
			System.out.print(cont + ":" + grauEntrada[cont] + " ");
			} else {
				break;
			}
		}
		
		return grauEntrada;
		
	}



	private static int[] grausDeSaidaDeCadaVertice(int[][] matrixAdjacente, int tamanhoGrafo) {
		int[] grauEntrada = new int [tamanhoGrafo];
		int grau = 0;
		
		for(int cont=0; cont<matrixAdjacente.length; cont ++) {
			for(int cont2=0; cont2<matrixAdjacente.length; cont2 ++) {
				
				if(matrixAdjacente[cont][cont2] == 1) {
					grau++;
				}
			}
			grauEntrada[cont]=grau;
			grau = 0;
			
		}
		System.out.println();
		System.out.print("Grau de saida:  ");
		for(int cont=0; cont<tamanhoGrafo; cont++) {
			if(cont<tamanhoGrafo) {
			System.out.print(cont + ":" + grauEntrada[cont] + " ");
			} else {
				break;
			}
		}
		
		return grauEntrada;
		
	}



	private static int[][] atribuirDadosMatriz(int[][] matrixAdjacente, int[] arrayNovidadeInt) {
		
		int par = 2;
		int impar = 3;
		int tamanho = (arrayNovidadeInt.length - 2) / 2;
		for(int cont=0; cont<tamanho; cont ++) {
			matrixAdjacente[arrayNovidadeInt[par]][ arrayNovidadeInt[impar]]=1;
			par = par+2;
			impar = impar+2;
		}
		
		System.out.print("    0  1  2  3  4  5  6  7  8  9 10 11 12");
		System.out.println();
		for(int cont=0; cont<matrixAdjacente.length; cont ++) {
			System.out.print(cont + " ");
			for(int cont2=0; cont2<matrixAdjacente.length; cont2 ++) {
				if(cont>9) {
					System.out.print(" " + matrixAdjacente[cont][cont2] + " ");	
				} else {
				System.out.print("  " + matrixAdjacente[cont][cont2]);	
				}
			}
			System.out.println();
			
		}
		return matrixAdjacente;
	}



	public static int[] TransformarArrayInt(String[] arrayString) {
		int[] arrayNovidadeInt = new int [arrayString.length];
		
		for(int cont=0; cont<arrayString.length; cont ++) {
			arrayNovidadeInt[cont]= Integer.parseInt(arrayString[cont]);
		}
		
		return arrayNovidadeInt;
	}
	
	
	private static void analisarVeticeIsolado(int[] grauEntrada, int[] grauSaida) {
        int contVertice=0;
        int []vertice = new int [grauEntrada.length];
        for(int cont=0; cont<grauEntrada.length; cont++){
            if(grauEntrada[cont]==0 && grauSaida[cont]==0){
                vertice[contVertice] =  cont;
                contVertice++;
            }
        }
        if(contVertice!=0){
            System.out.print("Vertices isolados: ");
            for(int cont=0; cont<vertice.length; cont++){
                System.out.print(vertice[cont] + " ");
            }
        } else {
            System.out.println();
            System.out.print("            Não existe vertice isolados");
        }
 
    }

}
