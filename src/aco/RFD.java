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

/**
 *
 * @author bishu
 */
public class RFD {

    //Variables
    int F[][], V[], path[][], pr[];
    double P[][], A[], str[];
    int cc, n;

    //Algo Parameters
    double Pf = 0.2, Ev = 1.0, Eu = 0.5, Ef = 100.0, D = 250.0;

    public RFD(int n) {
        P = new double[n][n];
        V = new int[n];
        A = new double[n];
        pr = new int[n];
        str = new double[n];

        for (int i = 0; i < n; i++) {
            V[i] = 0;
            A[i] = 10000.0;
            for (int j = 0; j < n; j++) {
                P[i][j] = 1.0;
            }
        }
        A[n - 1] = 0.0;
    }

    public double grad(int i, int j) {
        return A[i] - A[j];
    }

    public void getPath() throws Exception {
        int i, j, k, l, c, pi, pj, flag;
        double sum, max, z;
        int f[] = new int[n], x, y, w;
        path = new int[cc][n + 1];

        System.out.println("\nCyclomatic Complexity = " + cc
                + "\n No. of nodes = " + n);
        System.out.println("\nFeasible Set Matrix:\n");
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print(F[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("\n");

        c = cc;
        i = j = 0;
        pi = 0;
        pj = 1;

        while (c > 0) {
            path[pi][pj++] = i;
            sum = 0.0;

            i = 0;
            while (i < n - 1) {
                System.out.println("##################### (i = " + i + ") < (n-1 = "
                        + (n - 1) + ") ####################");
                V[i] = 1;
                //Thread.sleep(500);

                NEXT_NODE:
                {
                    z = 0.0;
                    l = 0;
                    for (k = 0; k < n; k++) {
                        if (F[i][k] == 1) {
                            z += grad(i, j);
                            f[l++] = k;
                            System.out.println("\t z= " + z + " k=" + k);
                        }
                    }
                    System.out.println("\n");
                    for (k = 0; k < l; k++) {
                        w = f[k];
                        sum = grad(i, w);
                        if (z != 0) {
                            if (sum > 0) {
                                P[i][w] = (sum / z);
                            } else if (sum < 0) {
                                sum = -sum;
                                P[i][w] = ((1.0) / z);
                            } else {
                                P[i][w] = Pf;
                            }
                        } else {
                            P[i][w] = Pf;
                        }
                    }

                    System.out.println("Altitude Matrix:\n");
                    printMatrix(A, n);
                    System.out.println("Probability Matrix:\n");
                    printMatrix(P, n);

                    if (l == 1) {
                        j = f[0];
                        System.out.println("\nOne in Feasible Set: " + j);
                    } else if (l > 1) {
                        max = 0.0;
                        x = y = 0;
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

                        System.out.println("");

                        if (x == 1) {
                            j = y;
                            System.out.println("\nFound Node " + j + " having Max Probability");
                        } else {
                            j = 0;
                            for (k = 0; k < l; k++) {
                                w = f[k];
                                System.out.print(P[i][w] + " ");
                                if (max == P[i][w] && w == n - 1 && V[w] == 0) {
                                    j = f[k];
                                    System.out.println("\tFound End Node");
                                    break;
                                }
                            }
                            if (j == 0) {
                                for (k = 0; k < l; k++) {
                                    w = f[k];
                                    System.out.print(P[i][w] + " ");

                                    if (max == P[i][w] && V[w] == 0) {
                                        j = f[k];
                                        System.out.println("\nNot Visited Node:" + w);
                                        break;
                                    }
                                }
                            }
                            if (j == 0) {
                                j = y;
                                System.out.println("No node selected. Select randomly.");
                            }

                            System.out.println("\n%%%%%%%%%%  SELECTED NODE: " + j + "  %%%%%%%%%%%%%%");
                        }

                    } else {
                        j = i + 1;
                        System.err.println("Invalid node/path in CFG.");
                    }
                }

                UPDATE:
                {
                    V[j] = 1;
                    sum = grad(i, j);
                    if (sum > 0) {
                        max = (sum * Ev);
                    } else if (sum < 0) {
                        sum = -sum;
                        max = (Eu / sum);
                    } else {
                        max = Ef;
                    }

                    if (j == n - 1) {
                        max = Ef;
                    }

                    A[i] = A[i] - max + D;

                    A[j] = A[j] + D;

                    path[pi][pj++] = j;
                    System.out.println("(i, j) = " + i + " " + j);

                    i = j;
                }

                //Thread.sleep(500);
                System.out.println("\tEnd of inner loop while(i<n-1) Iteration in c = " + (cc - c + 1) + " .\n");

            } //end inner while

            System.out.println("\t************** V ***********");
            printMatrix(V, n);
            System.out.println("\t************** A ***********");
            printMatrix(A, n);
            System.out.println("\t************** P ***********");
            printMatrix(P, n);

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

            if (flag == 1) {
                path[pi][0] = pj;
                str[pi] = A[path[pi][pj - 2]];
                System.out.println("\nTaken Alt of " + path[pi][pj - 2] + " as strength");

                pi++;
                pj = 1;
                i = 0;
                c--;
            } else {
                pj = 1;
                i = 0;
            }

            System.out.println("\t************** path ***********");
            printMatrix(path, pi, 1);
            System.out.println("\t************** str ***********");
            printMatrix(str, pi);

            System.out.println("\t@@@@@@@@@@@@@@@ END OF "
                    + ((cc - c)) + " ITERATION of while(c>0) @@@@@@@@@@@@@@@@\n\n");

        } //end while

        System.out.println("\nStrengths: ");
        for (int jj = 0; jj < cc; jj++) {
            System.out.println("str[]=" + str[jj]);
        }

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
            RFD R = new RFD(14);//t.n);
            R.F = t.M;
            R.n = t.n;
            R.cc = t.getComplexity();
            R.test();
            R.getPath();

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
            for (int i = 0; i < R.cc; i++) {
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

            for (int i = 0; i < R.cc; i++) {
                x = "";
                x += ("|      Path " + (i + 1));
                while (x.length() < 17) {
                    x += " ";
                }
                x += ("|      " + R.str[i]);
                while (x.length() < 30) {
                    x += "0";
                }
                while (x.length() < 37) {
                    x += " ";
                }
                x += "|        ";
                x += (R.pr[i]);
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

            /*System.out.println("Generated Test Data:");
             fl.pt2.setText(fl.pt2.getText() + "\n\nGenerated Test Data:\n");
             t.F.write("\nGenerated Test Data:\n");
             t.F.flush();
             for (int i = 0; i < t.cnt; i++) {
             System.out.print("\nFor path " + (i + 1) + ":\n\t");
             fl.pt2.setText(fl.pt2.getText() + "\nFor path " + (i + 1) + ":\t");
             t.F.write("\nFor path " + (i + 1) + ":\n\t");
             t.F.flush();
             for (int ip = 0; ip < t.b; ip++) {
             if (t.pri == 1) {
             System.out.print(t.input[ip] + "=" + (char) t.tc[i].input[ip] + "; ");
             t.F.write(t.input[ip] + "=" + (char) t.tc[i].input[ip] + ";  ");
             fl.pt2.setText(fl.pt2.getText() + t.input[ip] + "=" + (char) t.tc[i].input[ip] + "; ");
             } else {
             System.out.print(t.input[ip] + "=" + t.tc[i].input[ip] + "; ");
             t.F.write(t.input[ip] + "=" + t.tc[i].input[ip] + ";  ");
             fl.pt2.setText(fl.pt2.getText() + t.input[ip] + "=" + t.tc[i].input[ip] + "; ");
             }
             }
             System.out.println(" ");
             t.F.write("\n");
             t.F.flush();
             }*/
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
            C.n = R.n;//t.n;
            C.m = R.F;//t.M;
            CircleScrollable CR = new CircleScrollable(C);

        } catch (Exception e) {
            System.out.println("in main " + e);
        }
    }

}
