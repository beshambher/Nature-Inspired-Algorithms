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
public class ACO_test {

    int F[][], V[], path[][], pr[];
    double T[][], N[][], P[][], str[];
    int cc, n;

    public ACO_test(int z) {
        N = new double[z][z];
        T = new double[z][z];
        P = new double[z][z];
        V = new int[z];

        for (int i = 0; i < z; i++) {
            V[i] = 0;
            for (int j = 0; j < z; j++) {
                N[i][j] = 2.0;
                T[i][j] = 1.0;
                P[i][j] = 0.0;
            }
        }
    }

    public int getDepth(int i) {
        if (i == n - 1) {
            return 1;
        }
        int max = 0, d = 0;
        for (int j = i + 1; j < n; j++) {
            if (F[i][j] == 1) {
                d = getDepth(j);
                if (d > max) {
                    max = d;
                }
            }
        }
        return max + 1;
    }

    public void getPath() {
        int i, j, k, l, st, c, pi, pj, flag;
        double sum, max, z;
        int f[] = new int[n], x, y, w;
        path = new int[cc][n + 1];
        str = new double[cc];
        pr = new int[cc];

        System.out.println("\t Cyclomatic Complexity = " + cc
                + "\n\t No. of nodes = " + n);
        System.out.println("\n\tNode matrix: F:");
        printMatrix(F, n);
        System.out.println("\n\tProbability: P:");
        printMatrix(P, n);
        System.out.println("\n\tPheromones: T:");
        printMatrix(T, n);
        System.out.println("\n\tHeuristic Value: N:");
        printMatrix(N, n);
        System.out.println("\n");

        c = cc;
        i = j = 0;
        pi = 0;
        pj = 1;
        V[i] = 1;
        while (c > 0) {
            st = path[pi][pj++] = i;
            sum = 0.0;
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     Iteration begin for path " + (cc - c + 1)
                    + "    $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

            while (st < n - 1) {
                System.out.println("##################### (st = " + st + ") < (n-1 = "
                        + (n - 1) + ") ####################");

                z = 0.0;
                l = 0;
                for (k = 0; k < n; k++) {
                    if (F[i][k] == 1) {
                        z += (T[i][k] / N[i][k]);
                        f[l++] = k;
                        //System.out.println("Height of "+k+" is: "+getDepth(k));
                    }
                }
                for (k = 0; k < l; k++) {
                    w = f[k];
                    P[i][w] = (T[i][w] / (N[i][w] * z));
                }

                if (l == 1) {
                    j = f[0];
                    System.out.println("\nOne in Feasible Set: " + j);
                } else if (l > 1) {
                    max = 0.0;
                    x = y = 0;
                    System.out.print("\nMultiple in Feasible Set: ");
                    for (k = 0; k < l; k++) {
                        y = f[k];
                        System.out.print(y + " with PE " + P[i][y] + ", \t");
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

                    System.out.println("");

                    if (x == 1) {
                        j = y;
                        System.out.println("\nFound Node " + j + " having Max Probability.");
                    } else {
                        j = 0;
                        System.out.println("\n  More than one node has Max Probability.");
                        for (k = 0; k < l; k++) {
                            w = f[k];
                            System.out.print(P[i][w] + " ");
                            if (max == P[i][w] && w == n - 1) {
                                j = f[k];
                                System.out.println("\tFound End Node.");
                                break;
                            }
                        }
                        if (j == 0) {
                            for (k = 0; k < l; k++) {
                                w = f[k];
                                if (max == P[i][w] && V[w] == 0) {
                                    j = f[k];
                                    System.out.println("\nNot Visited Node: " + w);
                                    break;
                                }
                            }
                        }
                        if (j == 0) {
                            flag = 0;
                            for (k = 0; k < l; k++) {
                                w = f[k];
                                x = getDepth(w);
                                if (max == P[i][w] && x > flag) {
                                    j = f[k];
                                    flag = x;
                                    System.out.println("\nNo. of trailing nodes from mode " + w + ": " + x);
                                }
                            }
                        }
                        if (j == 0) {
                            Random rr = new Random();
                            y = rr.nextInt() % l;
                            j = f[y];
                            System.out.println("\nNo node selected. Select randomly.");
                        }
                        System.out.println("\n%%%%%%%%%%  SELECTED NODE: " + j + "  %%%%%%%%%%%%%%");
                    }

                } else {
                    j = i + 1;
                    System.err.println("Invalid node/path in CFG");
                }

                V[j] = 1;
                path[pi][pj++] = j;
                T[i][j] = T[i][j] + (1.0 / N[i][j]);
                N[i][j] *= 2.0;
                sum += T[i][j];
                System.out.println("(i, j) = (" + i + " " + j + ")");
                i = j;
                st = j;

                System.out.println("\n\tProbability: P:");
                printMatrix(P, n);
                System.out.println("\n\tPheromones: T:");
                printMatrix(T, n);
                System.out.println("\n\tHeuristic Value: N:");
                printMatrix(N, n);
                System.out.println("\n");

            }
            System.out.println("\tEnd of inner loop while(i<n-1) Iteration in c = " + (cc - c + 1) + ".\n");

            str[pi] = sum;

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

            if (flag == 1) {
                path[pi][0] = pj;
                pi++;
                pj = 1;
                i = 0;
                c--;

                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     Path " + (pi)
                        + " Generated and Stored   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

            } else {
                pj = 1;
                i = 0;
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     Redundant Path Generated."
                        + " Path Discarded   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");

            }
        } //end while

        System.out.println("\tEnd of outer loop while(c>0).\n");

        System.out.println("\t************** path ***********");
        printMatrix(path, pi, 1);
        System.out.println("\t************** str ***********");
        printMatrix(str, pi);

        System.out.println("\t@@@@@@@@@@@@@@@ END OF "
                + ((cc - c)) + " ITERATION of while(c>0) @@@@@@@@@@@@@@@@\n\n");

        for (k = 0; k < cc; k++) {
            w = 1;
            for (j = 0; j < cc; j++) {
                if (str[k] > str[j]) {
                    w++;
                }
            }
            pr[k] = cc - w + 1;
        }

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
    }

    public void printMatrix(double M[][], int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println("");
        }
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

    public void printMatrix(int M[][], int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < M[i][0]; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("\n\n");
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
            ACO_test A = new ACO_test(14);//t.n);
            //A.F = t.M;
            //A.n = t.n;
            //A.cc = t.getComplexity();
            A.test();
            A.getPath();

            System.out.println("Adjacency Matrix for CFG:\n");
            fl.pt2.setText("Adjacency Matrix for CFG:\n\n");
            t.F.write("Adjacency Matrix for CFG:\n\n");
            t.F.flush();
            t.print(fl.pt2);

            System.out.println("Total No. of Nodes: " + t.n + "\n");
            fl.pt2.setText(fl.pt2.getText() + "Total No. of Nodes: " + t.n + "\n\n");
            t.F.write("Total No. of Nodes: " + t.n + "\n\n");
            t.F.flush();

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
            System.out.println("________________________________________________________");
            System.out.println("|      Path      |      Strength     |     Priority    |");
            System.out.println("________________________________________________________");

            t.F.write("________________________________________________________\n");
            fl.pt2.setText(fl.pt2.getText() + "\n ______________________________\n");
            t.F.write("|      Path      |      Strength     |    Priority    |\n");
            fl.pt2.setText(fl.pt2.getText() + " |      Path       |      Strength     |    Priority    |\n");
            t.F.write("________________________________________________________\n");
            fl.pt2.setText(fl.pt2.getText() + " ______________________________\n");
            t.F.flush();

            for (int i = 0; i < A.cc; i++) {
                x = "";
                x += ("|      Path " + (i + 1));
                while (x.length() < 17) {
                    x += " ";
                }
                x += ("|      " + A.str[i]);
                while (x.length() < 30) {
                    x += "0";
                }
                while (x.length() < 37) {
                    x += " ";
                }
                x += "|        ";
                x += (A.pr[i]);
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

            /*System.out.println("\nTesting...");
             for (int i = 0; i < t.psz; i++) {
             System.out.println("***** Testing Path " + (i + 1) + " *****\n");
             t.F.write("\n***** Testing Path " + (i + 1) + " *****\n\n");
             t.F.flush();
             t.Test(t.path[i]); //Calling test function
             System.out.println(" ");
             t.F.write("\n");
             t.F.flush();
             }*/
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
            C.n = A.n;//t.n;
            C.m = A.F;//t.M;
            CircleScrollable CR = new CircleScrollable(C);

        } catch (Exception e) {
            System.out.println("in main " + e);
        }
    }

}
