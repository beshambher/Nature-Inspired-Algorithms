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
import java.util.Random;

/**
 *
 * @author bishu
 */
public class RFD_v2 {

    //Variables
    int F[][], M[][], Color[], path[][], pr[], N[];
    double P[][], A[], str[], S[][], H[][];
    int cc, n, c;
    Random rr = new Random();

    //Algo Parameters
    double Pf = 0.2, Ev = 0.5, Eu = 1000.0, Ef = 100.0, D = 250.0;

    public RFD_v2(int n) {
        P = new double[n][n];
        Color = new int[n];
        F = new int[n][n];
        M = new int[n][n];
        N = new int[n];
        A = new double[n];
        pr = new int[n];
        str = new double[n];
        S = new double[n][n];
        H = new double[n][n];
        c = 0;

        for (int i = 0; i < n; i++) {
            Color[i] = 0;
            A[i] = (rr.nextInt());
            if (A[i] < 0) {
                A[i] = -A[i];
            }
            A[i] = (A[i] % 5000) + 5000.0;
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

    public void getPath() {
        try {
            int i, j, k, l, t, m, pi, pj, flag, brk = 1;
            double g, max, z;
            int f[], x, y, w;
            path = new int[cc][n + 1];
            System.out.println("Altitude");
            printMatrix(A, n);

            System.out.println("\nCyclomatic Complexity = " + cc
                    + "\n No. of nodes = " + n);
            System.out.println("\nFeasible Set Matrix:\n");
            printMatrix(F, n);

            N[0] = 0;
            pi = t = 0;
            pj = m = 1;
            i = j = 0;

            while (brk > 0) {

                System.out.println("N[t] = " + N[t] + "\n");
                i = N[t++];
                Color[i] = 1;
                path[pi][pj++] = i;
                System.out.println("i = " + i + "\n");

                while (i < n - 1) {

                    System.out.println("(t, m) = " + t + " " + m);
                    System.out.println("^^^^^^^^^^^^ N ^^^^^^^^^^^");
                    printMatrix(N, m);

                    f = new int[n];
                    NEXT_NODE:
                    {
                        z = 0.0;
                        l = 0;
                        for (k = 0; k < n; k++) {
                            if (F[i][k] == 1) {
                                z += ((grad(i, k) > 0 ? grad(i, k) : 0) / S[i][k]);
                                f[l++] = k;
                                System.out.println("\t z= " + z + " i=" + i + " k=" + k + " l=" + l);
                            }
                        }
                        System.out.println("After calculating z. L = " + l + "\n");
                        for (k = 0; k < l; k++) {
                            w = f[k];
                            g = grad(i, w);
                            if (z != 0) {
                                if (g > 0) {
                                    P[i][w] = (g / S[i][w]) / z;
                                } else if (g < 0) {
                                    g = -g;
                                    P[i][w] = ((1.0 * S[i][w]) / g) / z;
                                } else {
                                    P[i][w] = Pf;
                                }
                            } else {
                                P[i][w] = Pf;
                            }
                        }

                        System.out.println("After calculating P[i][w].\n");

                        if (l == 1) {
                            j = f[0];
                            System.out.println("\nOne in Feasible Set: " + j);
                        } else if (l > 1) {
                            max = 0.0;
                            x = y = 0;
                            N[m++] = i;
                            System.out.print("Multiple in Feasible Set: ");
                            for (k = 0; k < l; k++) {
                                y = f[k];
                                System.out.print(y + " ");
                                if (P[i][y] > max) {
                                    max = P[i][y];
                                }
                            }
                            for (k = 0; k < l; k++) {
                                w = f[k];
                                if (max == P[i][w]) {
                                    x++;
                                    y = w;
                                }
                            }

                            if (x == 1) {
                                j = y;
                                System.out.println("\nFound Node " + j + " having Max Probability " + P[i][j]);
                            } else {
                                j = 0;
                                for (k = 0; k < l; k++) {
                                    w = f[k];
                                    if (max == P[i][w] && w == n - 1) {
                                        j = f[k];
                                        System.out.println("\tFound End Node");
                                        break;
                                    }
                                }
                                if (j == 0) {
                                    y = rr.nextInt();
                                    y = (y < 0 ? -y : y) % l;
                                    j = f[y];
                                    System.out.println("No node selected. Select randomly.");
                                }

                                System.out.println("\n%%%%%%%%%%  SELECTED NODE: " + j + "  %%%%%%%%%%%%%%");
                            }
                        }
                    } //Next Node

                    UPDATE:
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

                        A[i] = A[i] - max;
                        A[j] = A[j] + max;
                        System.out.println("\t\t pj = " + pj);
                        path[pi][pj++] = j;
                        System.out.println("\tEnd of (i, j) = " + i + " " + j + " F[i][j] = " + F[i][j]);
                        i = j;
                    } //Update

                    System.out.println("out of UPDATE");

                    try {
                        j = 0;
                        for (k = 0; k < n; k++) {
                            j += F[i][k];
                        }
                        if (j == 0) {
                            System.out.println("No further reachable node, breaking inner loop");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("In break check " + e);
                    }

                } //inner while

                System.out.println("pi = " + pi + " pj = " + pj);

                //Thread.sleep(500);
                flag = 1;
                for (k = 0; k < pi; k++) {
                    j = 0;
                    for (l = 0; l < path[k][0]; l++) {
                        if (path[pi][l] == path[k][l]) {
                            j++;
                        }
                    }

                    if (path[k][0] == pj && j == pj - 1) {
                        flag = 0;
                        break;
                    }
                }
                System.out.println("flag = " + flag);

                if (flag == 1) {
                    path[pi][0] = pj;
                    str[pi] = 0;
                    for (int ii = path[pi][0] - 1; ii > 2; ii--) {
                        str[pi] += grad(path[pi][ii], path[pi][ii - 1]);
                    }

                    System.out.println("flag is 1");

                    try {
                        System.out.println("\nTaken Alt of " + path[pi][pj - 2] + " as strength");
                    } catch (Exception e) {
                        System.out.println("In pj-2 statement " + e);
                    }
                    pi++;
                    pj = 1;
                    i = 0;
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     Path " + (pi)
                            + " Generated and Stored   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
                    printMatrix(path, pi, 1);
                } else {
                    pj = 1;
                    i = 0;
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     Redundant Path Generated."
                            + " Path Discarded   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
                }

                //System.out.println("************* F ***********");
                //printMatrix(F, n);
                brk = 0;
                for (k = 0; k < n; k++) {
                    for (int v = 0; v < n; v++) {
                        brk += F[k][v];
                    }
                }
                System.out.println("------------------------> end of an iteration of outer loop. brk=" + brk + "\n");
            } //Outer while

            c = pi;
            for (k = 0; k < c; k++) {
                w = 1;
                for (j = 0; j < c; j++) {
                    if (str[k] > str[j]) {
                        w++;
                    }
                }
                pr[k] = c - w + 1;
            }
        } catch (Exception e) {
            System.err.println("In getpaths() " + e);
        }
    }

    public void printResults(Genetic t, inputFile fl) throws Exception {

        String x;
        System.out.println("________________________________________________________");
        System.out.println("|      Path      |      Strength     |     Priority    |");
        System.out.println("________________________________________________________");

        t.F.write("________________________________________________________\n");
        fl.pt2.setText(fl.pt2.getText() + "\n _______________________________\n");
        t.F.write("|      Path      |      Strength     |    Priority    |\n");
        fl.pt2.setText(fl.pt2.getText() + " |      Path       |      Strength     |    Priority    |\n");
        t.F.write("________________________________________________________\n");
        fl.pt2.setText(fl.pt2.getText() + " ______________________________\n");
        t.F.flush();

        for (int i = 0; i < c; i++) {
            x = "";
            x += ("|      Path " + (i + 1));
            while (x.length() < 17) {
                x += " ";
            }
            x += ("|      " + str[i]);
            while (x.length() < 30) {
                x += "0";
            }
            while (x.length() < 37) {
                x += " ";
            }
            x += "|        ";
            x += (pr[i]);
            while (x.length() < 55) {
                x += " ";
            }
            x += "|";
            System.out.println(x);
            t.F.write(x + "\n");
            fl.pt2.setText(fl.pt2.getText() + "  " + x + " \n");
            t.F.flush();
        }
        System.out.println("________________________________________________________\n");
        t.F.write("________________________________________________________\n");
        fl.pt2.setText(fl.pt2.getText() + " ______________________________\n");
        t.F.flush();
    }

    public void test() {
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

    public void equal(int[][] A, int a, char q) {
        n = a;
        int temp = 0;
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

    public static void main(String args[]) {
        try {

            //Open file frame
            SourceFile s = new SourceFile();
            int a = 0;
            String S = "";
            //Getting the file from user. 
            s.showFile();
            while (s.jj < 1) {
                Thread.sleep(10);
            }

            /**
             * Genetic class object for calculating Feasible path set matrix and
             * cyclomatic complexity.
             */
            Genetic t = new Genetic();

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
            t.M[t.n - 1][t.n] = 1;
            t.n++;
            t.M[t.n - 1][t.n] = 1;
            t.n++;

            /**
             * ACO class object for path generation and path prioritization.
             */
            RFD_v2 R = new RFD_v2(t.n);
            R.equal(t.M, t.n, 'F');
            R.equal(t.M, t.n, 'M');
            R.cc = t.getComplexity();
            //R.test();
            /*
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
             };
             */
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

            System.out.println("\nIndependent paths: ");
            fl.pt2.setText(fl.pt2.getText() + "Independent paths: \n\n");
            t.F.write("Independent paths: \n\n");
            t.F.flush();
            for (int i = 0; i < R.c; i++) {
                System.out.print("Path " + (i + 1) + ":   ");
                t.F.write("Path " + (i + 1) + ":   ");
                fl.pt2.setText(fl.pt2.getText() + "Path " + (i + 1) + ":   ");
                for (int j = 1; j < R.path[i][0]; j++) {
                    System.out.print(R.path[i][j] + " ");
                    t.F.write(R.path[i][j] + " ");
                    fl.pt2.setText(fl.pt2.getText() + R.path[i][j] + "  ");
                }
                System.out.println("\n");
                t.F.write("\n");
                t.F.flush();
                fl.pt2.setText(fl.pt2.getText() + "\n");
            }
            
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
            System.out.println("in main " + e);
        }
    }

} //End of class.
