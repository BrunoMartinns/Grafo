package programacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

public class Grafo {
	
	public static void main(String args[]) throws IOException, ParseException, InterruptedException {
		
		String path = "C:\\Users\\bruno\\Downloads\\pequenoG.txt";
		String buffRead = " ";
		buffRead = new String(Files.readAllBytes(Paths.get(path)));
		String buff = buffRead.replace("\n", "\s");        //String buff = buffRead.replaceAll("\\r\\n|\\n", "");
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
