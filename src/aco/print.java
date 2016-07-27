/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

/**
 *
 * @author wmc
 */
public class print {

    private int i = 0;

    public void printString(String s) {
        int j = (i++) % 6;
        switch (j) {
            case 0:
                System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@ " + s + ": @@@@@@@@@@@@@@@@@@@@@@@@@");
                break;
            case 1:
                System.out.println("\n######################### " + s + ": #########################");
                break;
            case 2:
                System.out.println("\n************************* " + s + ": *************************");
                break;
            case 3:
                System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%% " + s + ": %%%%%%%%%%%%%%%%%%%%%%%%%");
                break;
            case 4:
                System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$ " + s + ": $$$$$$$$$$$$$$$$$$$$$$$$$");
                break;
            default:
                System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&& " + s + ": &&&&&&&&&&&&&&&&&&&&&&&&&");
        }
    }

    public void printObj(String s, Object obj) {
        System.out.println(s + obj);
    }

    public void printMatrix(int M[][], int n) {
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                System.out.print(M[k][j] + " ");
            }
            System.out.println("");
        }
    }

    public void printMatrix(double M[][], int n) {
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                System.out.print(M[k][j] + " ");
            }
            System.out.println("");
        }
    }

    public void printMatrix(int M[], int n) {
        for (int k = 0; k < n; k++) {
            System.out.print(M[k] + " ");
        }
        System.out.println("\n\n");
    }

    public void printMatrix(double M[], int n) {
        for (int k = 0; k < n; k++) {
            System.out.print(M[k] + " ");
        }
        System.out.println("\n\n");
    }

    public void printMatrix(int M[][], int n, int m) {
        for (int k = 0; k < n; k++) {
            for (int j = 1; j < M[k][0]; j++) {
                System.out.print(M[k][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("\n\n");
    }

    public void printMatrix(int M[][][], int n) {
        for (int l = 0; l < n; l++) {
                System.out.println("Path Sequence " + (l+1) + ": sub paths = "+M[l][0][0]);
            for (int k = 1; k < M[l][0][0]; k++) {
                System.out.println("Sub Path " + k + ":");
                for (int j = 1; j < M[l][k][0]; j++) {
                    System.out.print(M[l][k][j] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
        System.out.println("\n\n");
    }

}
