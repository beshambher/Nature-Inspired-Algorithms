/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author bishu
 */
public class RFD_v3 {

    //Variables
    int F[][], M[][], Color[], path[][], pr[];
    double P[][], A[], str[], S[][], H[][];
    int cc, n, c, nd;
    Queue N;
    Random rr = new Random();

    //Algo Parameters
    double Pf = 0.1, Ev = 0.5, Eu = 1000.0, Ef = 100.0, D = 250.0;

    public RFD_v3() {
    }

    public RFD_v3(int n) {
        P = new double[n][n];
        Color = new int[n];
        F = new int[n][n];
        M = new int[n][n];
        A = new double[n];
        pr = new int[n];
        str = new double[n];
        S = new double[n][n];
        H = new double[n][n];
        path = new int[n][2 * n];
        N = new Queue();
        nd = 20;
        c = 0;

        for (int i = 0; i < n; i++) {
            Color[i] = 0;
            //A[i] = (rr.nextInt());
            //if (A[i] < 0) {
            //  A[i] = -A[i];
            //}
            A[i] = (/*A[i] % */5000) + 5000.0;
            for (int j = 0; j < n; j++) {
                P[i][j] = 1.0;
                S[i][j] = 1.0;
                H[i][j] = 2.0;
            }
        }
        A[n - 1] = 0.0;
    }

    public double grad(int i, int j) {
        return A[i] - A[j];
    }

    public void moveDrops(Lexer T, inputFile FL) {
        try {
            int i, j, m, l, feasible;
            int k, w, x, y, pi, pj, temp;
            double g, z, max;

            m = j = l = 0;

            //Iterate till nd Drops i.e., move 'nd' Drops one by one.
            while (m < nd) {
                //while source list is not empty.
                equal(M, n, 'F');
                System.out.println("Drop No: " + (m + 1));
                //System.out.println("F:");
                //printMatrix(F, n);
                //System.out.println("P:");
                //printMatrix(P, n);

                N.add(0);
                N.print();
                c = pi = 0;
                for (k = 0; k < n; k++) {
                    Color[k] = 0;
                }

                while (!N.isEmpty()) {
                    //while feasible set of current node is not empty.
                    N.print();
                    i = N.remove();  //Get first node from source list.
                    /*if (getOutDegree(i) < 1) {
                        i = N.remove();
                    }*/
                    feasible = 1;
                    pj = 1;
                    path[pi][pj++] = i;

                    while (feasible > 0) {
                        int f[] = new int[n];  //f[]: Feasible set of current node 'i'.
                        //System.out.println("Inside l>0 loop.");
                        Color[i]++;  //Set its color as black.
                        print("i = ", i);

                        //Add i to Source list if it has multiple outgoing edges.
                        if (getOutDegree(i) > 1) {
                            N.add(i);
                        }

                        CALCULATE_VALUES:
                        {
                            //Get sum of gradients for all edges (i -> k).
                            z = 0.0;
                            l = 0;
                            for (k = 0; k < n; k++) {
                                if (F[i][k] == 1) {
                                    z += ((grad(i, k) > 0 ? grad(i, k) : -grad(i, k)) / S[i][k]);
                                    f[l++] = k;
                                }
                            }
                            //Calculate probability of all k in f[].
                            for (k = 0; k < l; k++) {
                                w = f[k];
                                g = grad(i, w);
                                if (g > 0) {
                                    P[i][w] = (g / S[i][w]) / z;
                                } else if (g < 0) {
                                    g = -g;
                                    P[i][w] = ((1.0 * S[i][w]) / g) / z;
                                } else {
                                    P[i][w] = Pf;
                                }
                                if (z == 0) {
                                    P[i][w] = Pf;
                                }
                            }

                        } //CALCULATE_VALUES Ends.

                        /**
                         * Select next node 'j' according to the rules given:-
                         * 1) Choose node j with maximum probability. If two or
                         * more nodes have same probability then follow rule 2.
                         * 2) Choose j if j is end node else choose j with
                         * maximum out degree. If two nodes have same out degree
                         * then select a node randomly.
                         */
                        NEXT_NODE:
                        {
                            if (l == 1) {
                                j = f[0];  //Only one node in feasible set.                                
                            } else if (l > 1) {
                                //More than one node in feasible set.
                                max = 0.0;
                                x = y = 0;
                                //System.out.print("Feasibe set of " + i + " is: ");
                                //Get node with maximum probability.
                                for (k = 0; k < l; k++) {
                                    y = f[k];
                                    //System.out.print(y + " ");
                                    if (P[i][y] > max) {
                                        max = P[i][y];
                                    }
                                }
                                //System.out.println("");
                                //Check how many nodes have same maximum probability. Store it in 'x'.
                                for (k = 0; k < l; k++) {
                                    w = f[k];
                                    if (max == P[i][w]) {
                                        x++;
                                        y = w;
                                    }
                                }
                                //Apply Rules.
                                if (x == 1) {
                                    /**
                                     * * * * * APPLY RULE: 1 * * * * *
                                     */
                                    j = y;  //Only one node with maximum probability.
                                } else {
                                    //More than one nodes have same maximum probability.
                                    /**
                                     * * * * * APPLY RULE: 2 * * * * *
                                     */
                                    j = 0;
                                    //Check for end node.
                                    for (k = 0; k < l; k++) {
                                        w = f[k];
                                        if (max == P[i][w] && w == n - 1) {
                                            j = f[k];  //End node found.
                                            break;
                                        }
                                    }
                                    //No end node found.
                                    if (j == 0) {
                                        //Get maximum out degree.
                                        x = y = 0;
                                        for (k = 0; k < l; k++) {
                                            w = f[k];
                                            temp = getOutDegree(w);
                                            if (max == P[i][w] && y < temp) {
                                                y = temp;
                                                x = w;
                                            }
                                        }
                                        //Check how many nodes have maximum out degree.
                                        temp = 0;
                                        LinkedList L = new LinkedList();
                                        for (k = 0; k < l; k++) {
                                            w = f[k];
                                            if (max == P[i][w] && getOutDegree(w) == y) {
                                                L.add(w);
                                                temp++;
                                            }
                                        }
                                        //If one node have maximum out degree. Select it.
                                        if (temp == 1) {
                                            j = x;
                                        } else if (temp > 1) {
                                            //More than one nodes have maximum out degree. Select Randomly.
                                            w = L.size();
                                            y = rr.nextInt();
                                            y = (y < 0 ? -y : y) % w;
                                            //System.out.println("Random index y = " + y);
                                            j = (Integer) L.get(y);
                                        } else {
                                            System.err.println("\t Fatal Error in out degree.");
                                        }
                                    }
                                    //Selected next node is 'j'.
                                    //System.out.println("\n%%%%%%%%%%  SELECTED NODE: " + j + "  %%%%%%%%%%%%%%");
                                }
                            } else {
                                feasible = 0;
                            }
                        } //NEXT_NODE Ends.

                        //System.out.println("Before UPDATE PARAMETERS.");
                        UPDATE_PARAMETERS:
                        {
                            F[i][j] = 0;
                            g = grad(i, j);
                            S[i][j] = S[i][j] + (1.0 / H[i][j]);
                            H[i][j] *= 2;
                            if (g > 0) {
                                max = (g * Ev);
                            } else if (g < 0) {
                                g = -g;
                                max = (Eu / g);
                            } else {
                                max = Ef;
                            }
                            //System.out.println("After max.");

                            A[i] = A[i] - max;
                            A[j] = A[j] + max;

                            if (Color[j] < 1) {
                                path[pi][pj++] = j;
                                if (j == n - 1) {
                                    feasible = 0;
                                }
                            }

                            i = j;

                            if (Color[j] > 1) {
                                path[pi][pj++] = j;
                                feasible = 0;
                            }
                            //System.out.println("i = j = " + i + " pi = " + pi + " pj = " + pj);
                            //System.out.println("End of update section.");

                        } //UPDATE_PARAMETERS Ends.

                    } //while (feasible > 0) ends.

                    //System.out.println("Before RECORD PATH.");
                    RECORD_PATH:
                    {
                        path[pi][0] = pj;
                        str[pi] = 0;
                        for (int ii = 0; ii < path[pi][0] - 1; ii++) {
                            str[pi] += (S[path[pi][ii]][path[pi][ii + 1]]);
                        }
                        c++;
                        pi++;
                        pj = 1;
                        N.print();
                    } //RECORD_PATH Ends.
                    //System.out.println("End of RECORD PATH. \t \t pj = " + pj);

                } //while (N.isEmpty) ends.

                printMatrix(path, pi, 1);
                //printMatrix(S, n);
                //printMatrix(A, n);
                //printMatrix(F, n);

                for (k = 0; k < c; k++) {
                    w = 1;
                    for (j = 0; j < c; j++) {
                        if (str[k] > str[j]) {
                            w++;
                        }
                    }
                    pr[k] = c - w + 1;
                }

                FL.pt2.setText(FL.pt2.getText() + "\n*************************************************************\n");
                FL.pt2.setText(FL.pt2.getText() + "Drop No: " + (m + 1) + "\n\n");
                printResults(T, FL);
                m++;
            } //while (m < nd) ends.

        } catch (Exception e) {
            System.err.println("In moveDrops() " + e);
        }
    }

    public void setStrength(ACO_v1 Ac) {
        int i, j, k, l;
        for (i = 0; i < Ac.cc; i++) {
            Ac.str[i] = 0.0;
            for (j = 1; j < Ac.path[i][0] - 1; j++) {
                k = Ac.path[i][j];
                l = Ac.path[i][j + 1];
                Ac.str[i] += S[k][l];
            }
        }
        Ac.setPriority();
    }

    public int getOutDegree(int j) {
        int i = 0;
        for (int k = 0; k < n; k++) {
            i += F[j][k];
        }
        return i;
    }

    public void printResults(Lexer t, inputFile fl) throws Exception {

        System.out.println("\n Paths Generated: ");
        fl.pt2.setText(fl.pt2.getText() + "Paths Generated: \n\n");
        t.F.write("Paths Generated: \n\n");
        t.F.flush();
        for (int i = 0; i < c; i++) {
            System.out.print("Path " + (i + 1) + ":   ");
            t.F.write("Path " + (i + 1) + ":   ");
            fl.pt2.setText(fl.pt2.getText() + "Path " + (i + 1) + ":   ");
            for (int j = 1; j < path[i][0]; j++) {
                System.out.print(path[i][j] + " ");
                t.F.write(path[i][j] + " ");
                fl.pt2.setText(fl.pt2.getText() + path[i][j] + "  ");
            }
            System.out.println("\n");
            t.F.write("\n");
            t.F.flush();
            fl.pt2.setText(fl.pt2.getText() + "\n");
        }

    }

    public void equal(int[][] A, int a, char q) {
        n = a;
        int temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp = A[i][j];
                if (q == 'F') {
                    F[i][j] = temp;
                } else {
                    M[i][j] = temp;
                }
            }
        }
    }

    public void test1() {
        cc = 4;
        n = 14;
        F = new int[][]{
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    public void test2() {
        cc = 12;
        n = 48;
        F = new int[][]{
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,}
        };
    }

    public void print(String s, Object obj) {
        System.out.println(s + obj);
    }

    public void printMatrix(int M[][], int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("\n\n");
    }

    public void printMatrix(double M[][], int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("\n\n");
    }

    public void printMatrix(int M[][], int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < M[i][0]; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("\n\n");
    }

    public void printMatrix(int M[], int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(M[i] + " ");
        }
        System.out.println("\n\n");
    }

    public void printMatrix(double M[], int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(M[i] + " ");
        }
        System.out.println("\n\n");
    }

    public static void run(SourceFile s) {
        try {
            System.out.println("in RFD run()");

            //Open file frame
            int a = 0;
            String S = "";
            //Getting the file from user. 
            s.showFile();
            while (s.jj < 1) {
                Thread.sleep(10);
            }

            /**
             * Lexer class object for calculating Feasible path set matrix and
             * cyclomatic complexity.
             */
            Lexer t = new Lexer();

            //Input Output frame        
            inputFile fl = new inputFile(s.file);
            s.f.dispose();

            BufferedReader bf = new BufferedReader(new FileReader(s.file));
            while ((S = bf.readLine()) != null) {
                fl.pt1.setText(fl.pt1.getText() + S + "\n");
            }
            bf.close();
            t.F.write("Source File: \"" + s.file.getName() + "\"");
            t.F.write("\n\n");
            t.F.flush();
            String file = s.file.getPath();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //Storing starting time of execution.
            Calendar Cal = new GregorianCalendar();
            int mint = Cal.get(Calendar.MINUTE);
            int sec = Cal.get(Calendar.SECOND);

            /**
             * Checking the extention of file by default we are processing C++
             * files This will detect .c extention and inform the program that
             * the input file is a C file.
             */
            if (s.file.getName().charAt((s.file.getName().length() - 1)) == 'c') {
                t.fex = 1;
            }

            /**
             * check() function to generate Feasible path set.
             */
            t.check(br, 0);
            if (t.n > 0) {
                t.M[t.n - 1][t.n] = 1;
                t.n++;
                t.M[t.n - 1][t.n] = 1;
                t.n++;
            } else {
                t.M[0][1] = t.M[1][2] = 1;
                t.n = 2;
            }

            /*test
             t.n = 14;
             t.M = new int[][]{
             {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
             {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
             {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
             {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
             }; *
            t.n = 43;
            t.M = new int[][]{
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            }; */
            
            t.n = 40;
            t.M = new int[][]{
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
            /**
             * ACO class object for path generation and path prioritization.
             */
            RFD_v3 R = new RFD_v3(t.n);
            R.equal(t.M, t.n, 'F');
            R.equal(t.M, t.n, 'M');
            R.cc = t.getComplexity();
            //R.test();

            System.out.println("____________________________________________"
                    + "______ACO__BEGIN___________________________________");
            /*
             Generate complete paths by ACO
             */
            ACO_v1 A = new ACO_v1(t.n);
            A.F = t.M;
            A.n = t.n;
            A.cc = t.getComplexity();
            A.getPath();

            System.out.println("____________________________________________"
                    + "______ACO__END___________________________________");
            /*
             R.getPath(); /*
             System.out.println("t.M After getpath() ");
             R.printMatrix(t.M, t.n);
             System.out.println("R.M After getpath() ");
             R.printMatrix(R.M, t.n);
             System.out.println("R.F After getpath() ");
             R.printMatrix(R.F, t.n); */
            System.out.println("Adjacency Matrix for CFG:\n");
            fl.pt2.setText("Adjacency Matrix for CFG:\n\n");
            t.F.write("Adjacency Matrix for CFG:\n\n");
            t.F.flush();
            t.print(fl.pt2);

            System.out.println("Total No. of Nodes: " + t.n + "\n");
            fl.pt2.setText(fl.pt2.getText() + "Total No. of Nodes: " + t.n + "\n\n");
            t.F.write("Total No. of Nodes: " + t.n + "\n\n");
            t.F.flush();

            System.out.println("Cyclomatic Complexity: E-N+2P = " + R.cc);
            fl.pt2.setText(fl.pt2.getText() + "Cyclomatic Complexity: E-N+2P = " + R.cc + "\n\n");
            t.F.write("Cyclomatic Complexity: E-N+2P = " + R.cc + "\n\n");
            t.F.flush();

            //Move 10 Drops.
            R.moveDrops(t, fl);
            R.setStrength(A);

            fl.pt2.setText(fl.pt2.getText() + "\n\n*************************************************************\n");
            System.out.println("\nIndependent paths: ");
            fl.pt2.setText(fl.pt2.getText() + "Independent paths: \n\n");
            t.F.write("Independent paths: \n\n");
            t.F.flush();

            for (int i = 0; i < A.cc; i++) {
                System.out.print("Path " + (i + 1) + ":   ");
                t.F.write("Path " + (i + 1) + ":   ");
                fl.pt2.setText(fl.pt2.getText() + "Path " + (i + 1) + ":   ");
                for (int j = 1; j < A.path[i][0]; j++) {
                    System.out.print(A.path[i][j] + " ");
                    t.F.write(A.path[i][j] + " ");
                    fl.pt2.setText(fl.pt2.getText() + A.path[i][j] + "  ");
                }
                System.out.println("\n");
                t.F.write("\n");
                t.F.flush();
                fl.pt2.setText(fl.pt2.getText() + "\n");
            }

            String x;
            System.out.println(" _________________________________________________________");
            System.out.println("|      Path      |       Strength       |     Priority    |");
            System.out.println(" _________________________________________________________");

            t.F.write("__________________________________________________________\n");
            fl.pt2.setText(fl.pt2.getText() + "\n  _____________________________________\n");
            t.F.write("  |      Path       |               Strength              |   Priority  |\n");
            fl.pt2.setText(fl.pt2.getText() + "  |      Path       |               Strength              |   Priority  |\n");
            t.F.write("__________________________________________________________\n");
            fl.pt2.setText(fl.pt2.getText() + "  _____________________________________\n");
            t.F.flush();

            for (int i = 0; i < A.cc; i++) {
                x = "";
                x += ("|      Path " + (i + 1));
                while (x.length() < 17) {
                    x += " ";
                }
                x += ("|   " + A.str[i]);
                while (x.length() < 30) {
                    x += "0";
                }
                while (x.length() < 38) {
                    x += "0";
                }
                x = x.toString().substring(0, 38);
                x += "   |        ";
                x += (A.pr[i]);
                while (x.length() < 58) {
                    x += " ";
                }
                x += "|";
                System.out.println(x);
                t.F.write(x + "\n");
                fl.pt2.setText(fl.pt2.getText() + "  " + x + "\n");
                t.F.flush();
            }
            System.out.println(" _________________________________________________________\n");
            t.F.write("___________________________________________________________\n");
            fl.pt2.setText(fl.pt2.getText() + "  ______________________________________\n");
            t.F.flush();

            t.FF.close();

            //This gives a separate JFrame for nodes desrciption
            Nodes ND = new Nodes();
            System.out.println("Func Array:");
            ND.pt.setText("Node:  Desription\n");
            for (int i = 0; i < t.n; i++) {
                System.out.println("Node " + (i) + ": " + t.func[i].s);
                ND.pt.setText(ND.pt.getText() + "\nNode " + (i) + ": " + t.func[i].s);
            }

            System.out.println(" ");
            ND.pt.setText(ND.pt.getText() + "\n\nNote: Here * denotes Null.");
            ND.setVisible(true);

            System.out.println("\nExecution Start Time: " + mint + " minutes " + sec + " seconds");

            /*Comparing starting time of execution from current time
             to calculate total time of execution. */
            Cal = new GregorianCalendar();
            int mint2 = Cal.get(Calendar.MINUTE);
            int sec2 = Cal.get(Calendar.SECOND);
            System.out.println("\n\nExecution End Time: " + mint2 + " minutes " + sec2 + " seconds");
            mint = mint * 60 + sec;
            mint2 = mint2 * 60 + sec2;
            sec = (mint2 - mint) % 60;
            mint = (mint2 - mint) / 60;
            fl.pt2.setText(fl.pt2.getText() + "\n\nExecution Time: " + mint + " minutes " + sec + " seconds");
            t.F.write("\n\nExecution Time: " + mint + " minutes " + sec + " seconds");

            fl.pt2.setText(fl.pt2.getText() + "\n\n\n");
            t.F.flush();
            t.F.close(); //System.out.println("\n\nFile closed");

            //This gives CGF in GUI in separate JFrame
            CirclePane C = new CirclePane();
            C.n = t.n;
            C.m = t.M;
            CircleScrollable CR = new CircleScrollable(C);

        } catch (Exception e) {
            System.out.println("in run " + e);
        }
    }

    public static void main(String args[]) {
        try {
            run(new SourceFile());
        } catch (Exception e) {
            System.out.println("in main " + e);
        }
    }

} //End of class.
