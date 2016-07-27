/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import static java.lang.Math.abs;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author bishu
 */
public class cuckoo_v3 {

    //Variables
    int F[][], M[][], pr[], path[][][], str[], f[][];
    int cc, n;
    private static print pn = new print();
    private Queue N;

    public cuckoo_v3() {
    }

    public cuckoo_v3(int n) {
        F = new int[n][n];
        M = new int[n][n];
        pr = new int[n];
        N = new Queue();
        cc = 0;
    }

    public void setEqual() {
        try {
            int temp;
            f = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    temp = M[i][j];
                    F[i][j] = temp;
                }
            }
        } catch (Exception e) {
            System.err.println("In setEqual() " + e);
        }
    }

    public int getOutDegree(int j) {
        int i = 0;
        for (int k = 1; k < n; k++) {
            i += F[j][k];
        }
        return i;
    }

    public int getInDegree(int j) {
        int i = 0;
        for (int k = 1; k < n; k++) {
            i += F[k][j];
        }
        return i;
    }

    public void initPathSequence(int a[][], int n, Lexer_v4 T, inputFile FL) {
        try {
            path = new int[n][100][100];
            cc = n;
            System.out.println("before loop in initPathSequence array: ");
            for (int i = 0; i < n; i++) {
                path[i][1] = a[i];
                setSequences(i, T, FL);
            }
            System.out.println("Out of loop in initPathSequence");
            pn.printMatrix(path, n);
            System.out.println("\n\n");
            setStrength();
            printResults(T, FL);
        } catch (Exception e) {
            System.out.println("Error in initPathSequence " + e);
        }
    }

    public void setStrength() {
        str = new int[cc];
        int i, s;
        Random rr = new Random();
        setEqual();
        for (int l = 0; l < cc; l++) {
            str[l] = 0;
            for (int k = 1; k < path[l][0][0]; k++) {
                for (int j = 1; j < path[l][k][0]; j++) {
                    i = path[l][k][j];
                    s = (abs(rr.nextInt()) % 10 + 1);
                    str[l] += (s * getOutDegree(i) * getInDegree(i));
                }
            }
        }
    }

    public void setSequences(int i, Lexer_v4 T, inputFile FL) {
        try {
            int current, next, feasible;
            int k, l, x, y, pi, pj;
            pi = 2;

            next = 0;
            setEqual();
            N = new Queue();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Sequence No: "
                    + (i + 1) + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            for (k = 2; k < path[i][1][0]; k++) {
                x = path[i][1][k - 1];
                y = path[i][1][k];
                F[x][y] = 0;
            }
            pn.printMatrix(F, n);
            for (k = 1; k < n; k++) {
                x = getOutDegree(k);
                while (x > 0) {
                    N.add(k);
                    x--;
                }
            }
            System.out.println(" N: ");
            N.print();

            while (!N.isEmpty()) {
                //while feasible set of current node is not empty.
                current = N.remove();  //Get first node from source list.
                //while (getOutDegree(current) == 0) {
                //  current = N.remove();
                //}
                pj = 1;

                if (getOutDegree(current) == 0) {
                    feasible = 0;
                } else {
                    feasible = 1;
                    path[i][pi][pj] = current;
                    pj++;
                }

                //System.out.println("before feasible > 0 loop.");
                while (feasible > 0) {
                    //System.out.println("Inside l>0 loop.");
                    //Get sum of gradients for all edges (i -> k).
                    l = 0;
                    for (k = 1; k < n; k++) {
                        if (F[current][k] == 1) {
                            l = k;
                            break;
                        }
                    }
                    /**
                     * Select next node 'j' randomly.
                     */
                    if (l > 0) {
                        next = l;  //Select one node in feasible set.
                    } else {
                        feasible = 0;
                        break; //No node in feasible set
                    }
                    //System.out.println("Before UPDATE PARAMETERS.");
                    F[current][next] = 0;
                    path[i][pi][pj] = next;
                    pj++;

                    //if (next == n - 1) {
                    //  feasible = 0;
                    //}
                    System.out.println("i = " + current + " ->  j = " + next);
                    current = next;
                    //System.out.println("i = j = " + i + " pi = " + pi + " pj = " + pj);
                } //while (feasible > 0) ends.

                System.out.println("End of while (feasible > 0). pj = " + pj);
                path[i][pi][0] = pj;
                for (k = 1; k < path[i][pi][0]; k++) {
                    System.out.print(path[i][pi][k] + " ");
                }
                if (pj > 1) {
                    pi++;
                }
                System.out.println("\nN is: ");
                N.print();
                //System.out.println("End of RECORD PATH. \t \t pj = " + pj);
            } //while (N.isEmpty) ends.

            path[i][0][0] = pi;
            pn.printMatrix(F, n);
            System.out.print("\n*****************************path[i][0][0] = pi = " + path[i][0][0] + "********************************\n");

        } catch (Exception e) {
            System.err.println("In setSequences(" + (i + 1) + ") " + e);
        }
    }

    public void printResults(Lexer_v4 t, inputFile fl) {
        try {
            t.F.flush();
            for (int i = 0; i < cc; i++) {
                fl.pt2.setText(fl.pt2.getText() + "\n*************************************************************\n");
                fl.pt2.setText(fl.pt2.getText() + " Sequence No: " + (i + 1) + "\t Fitness: " + str[i] + "\n\n");

                System.out.print("Main Path: ");
                t.F.write("Main Path: ");

                fl.pt2.setText(fl.pt2.getText() + "Main Path: \t");
                for (int j = 1; j < path[i][1][0]; j++) {
                    System.out.print(path[i][1][j] + " ");
                    t.F.write(path[i][1][j] + " ");
                    fl.pt2.setText(fl.pt2.getText() + path[i][1][j] + " ");
                }

                System.out.println("");
                fl.pt2.setText(fl.pt2.getText() + "\n\n");
                t.F.write("\n");
                t.F.flush();

                for (int k = 2; k < path[i][0][0]; k++) {
                    System.out.print("Sub Path " + (k - 1) + ": ");
                    t.F.write("Sub Path" + (k - 1) + ": ");
                    fl.pt2.setText(fl.pt2.getText() + "Sub Path " + (k - 1) + ": \t");
                    for (int j = 1; j < path[i][k][0]; j++) {
                        System.out.print(path[i][k][j] + " ");
                        t.F.write(path[i][k][j] + " ");
                        fl.pt2.setText(fl.pt2.getText() + path[i][k][j] + " ");
                    }
                    System.out.println("");
                    fl.pt2.setText(fl.pt2.getText() + "\n");
                    t.F.write("\n");
                    t.F.flush();
                }
                System.out.println("\n");
                t.F.write("\n");
                t.F.flush();
            }

        } catch (Exception e) {
            System.err.println("In printResults() " + e);
        }
    }

    public void equal(int[][] A, int a, char q) {
        try {
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
        } catch (Exception e) {
            System.err.println("In equal() " + e);
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

    public static void run(File s) {
        try {
            System.out.println("in RFD run()");

            //Open file frame
            int a = 0;
            String S = "", HT = "";
            //Getting the file from user. 
            /*s.showFile();
            while (s.jj < 1) {
                Thread.sleep(10);
            }
            s.f.setVisible(false);

            /**
             * Lexer_v4 class object for calculating Feasible path set matrix
             * and cyclomatic complexity.
             */
            Lexer_v4 t = new Lexer_v4();

            //Input Output frame        
            inputFile fl = new inputFile(s);
            a = 1;
            BufferedReader bf = new BufferedReader(new FileReader(s));
            while ((S = bf.readLine()) != null) {
                while (S.contains("#") || S.contains("namespace")) {
                    fl.pt1.setText(fl.pt1.getText() + "0.   " + S + "\n");
                    S = bf.readLine();
                }
                fl.pt1.setText(fl.pt1.getText() + (a++) + ".   " + S + "\n");
            }
            bf.close();

            t.F.write("Source File: \"" + s.getName() + "\"");
            t.F.write("\n\n");
            t.F.flush();
            String file = s.getPath();

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
            if (s.getName().charAt((s.getName().length() - 1)) == 'c') {
                t.fex = 1;
            }

            System.out.println("____________________________________________"
                    + "______LEXER__BEGIN___________________________________");
            /**
             * getCFG() function to generate Feasible path set.
             */
            t.M[0][1] = 1;
            t.getCFG(br);
            /*t.cc = 4;
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
            };*/

            System.out.println("____________________________________________"
                    + "______LEXER__END___________________________________\n\n");

            /**
             * ACO class object for path generation and path prioritization.
             */
            cuckoo_v3 R = new cuckoo_v3(t.n);
            R.equal(t.M, t.n, 'F');
            R.equal(t.M, t.n, 'M');
            //R.test();

            System.out.println("____________________________________________"
                    + "______ACO__BEGIN___________________________________");
            /*
             Generate complete paths by ACO
             */
            ACO_v4 A = new ACO_v4(t.n);
            A.F = t.M;
            A.n = t.n;
            A.cc = t.cc;
            A.getPath();
            pn.printMatrix(A.path, A.cc, 1);

            System.out.println("____________________________________________"
                    + "______ACO__END___________________________________");

            System.out.println("____________________________________________"
                    + "______CUCKOO__BEGIN___________________________________");
            R.initPathSequence(A.path, A.cc, t, fl);
            System.out.println("____________________________________________"
                    + "______CUCKOO__END___________________________________");

            /*
             R.getPath(); /*
             System.out.println("t.M After getpath() ");
             R.printMatrix(t.M, t.n);
             System.out.println("R.M After getpath() ");
             R.printMatrix(R.M, t.n);
             System.out.println("R.F After getpath() ");
             R.printMatrix(R.F, t.n);
             System.out.println("Adjacency Matrix for CFG:\n");
             fl.pt2.setText("Adjacency Matrix for CFG:\n\n");
             t.F.write("Adjacency Matrix for CFG:\n\n");
             t.F.flush();
             t.print(fl.pt2); */
            fl.pt2.setText(fl.pt2.getText() + "\n*************************************************************\n\n");
            System.out.println("Total No. of Nodes: " + t.n + "\n");
            fl.pt2.setText(fl.pt2.getText() + "Total No. of Nodes: " + (t.n - 1) + "\n\n");
            t.F.write("Total No. of Nodes: " + t.n + "\n\n");
            t.F.flush();

            System.out.println("Cyclomatic Complexity: E-N+2P = " + R.cc);
            fl.pt2.setText(fl.pt2.getText() + "Cyclomatic Complexity: E-N+2P = " + R.cc + "\n");
            t.F.write("Cyclomatic Complexity: E-N+2P = " + R.cc + "\n\n");
            t.F.flush();

            //Thread.sleep(1000);
            //Move 10 Drops.
            //R.moveDrops(t, fl);
            //R.setStrength(A);
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

            /* String x;
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

             for (int i = 0; i < R.cc; i++) {
             x = "";
             x += ("|      Path " + (i + 1));
             while (x.length() < 17) {
             x += " ";
             }
             x += ("|   " + R.str[i]);
             while (x.length() < 30) {
             x += "0";
             }
             while (x.length() < 38) {
             x += "0";
             }
             x = x.toString().substring(0, 38);
             x += "   |        ";
             x += (R.pr[i]);
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
             t.F.flush(); */
            t.FF.close();

            /*This gives a separate JFrame for nodes desrciption
             Nodes ND = new Nodes();
             System.out.println("Func Array:");
             ND.pt.setText("Node:  Desription\n");
             for (int i = 0; i < t.n; i++) {
             System.out.println("Node " + (i) + ": " + t.func[i].s);
             ND.pt.setText(ND.pt.getText() + "\nNode " + (i) + ": " + t.func[i].s);
             }

             System.out.println(" ");
             ND.pt.setText(ND.pt.getText() + "\n\nNote: Here * denotes Null.");
             ND.setVisible(true); */
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

            /*This gives CGF in GUI in separate JFrame
             CirclePane C = new CirclePane();
             C.n = t.n;
             C.m = t.M;
             CircleScrollable CR = new CircleScrollable(C);*/
        } catch (Exception e) {
            System.out.println("in run " + e);
        }
    }

    /*public static void main(String args[]) {
        try {
            run(new SourceFile());
        } catch (Exception e) {
            System.out.println("in main " + e);
        }
    }*/

} //End of class.
