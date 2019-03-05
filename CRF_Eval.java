import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CRF_Eval {
    // measure CONLL score of the file
    public static void coNllF1Score(String inputPath) {
        BufferedReader br = null;

        List<String> correctAnnotationList = new ArrayList<>();
        List<String> predictedAnnotationList = new ArrayList<>();

        String[] annotationArray = {"PERSON", "ORGANIZATION", "LOCATION", "DATE", "MONEY", "PERCENT", "TIME"};

        for (int i=0; i<7; i++) {
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), StandardCharsets.UTF_8));
                String line = br.readLine();
                StringBuilder correctAnnotation = new StringBuilder();
                StringBuilder predictedAnnotation = new StringBuilder();

                //String annotation = "TIME".toUpperCase();
                String annotation = annotationArray[i];
                System.out.println(annotation);

                while (line != null) {
                    if (!line.isEmpty()) {
                        String[] terms = line.split("\t"); // token - prediction - annotation

                        if (terms[2].equals(annotation)) {
                            correctAnnotation.append(" " + terms[0]);
                        } else if (correctAnnotation.length() > 0) {
                            correctAnnotationList.add(correctAnnotation.toString().substring(1, correctAnnotation.length()));
                            correctAnnotation.delete(0, correctAnnotation.length());
                        }
                        if (terms[1].equals(annotation)) {
                            predictedAnnotation.append(" " + terms[0]);
                        } else if (predictedAnnotation.length() > 0) {
                            predictedAnnotationList.add(predictedAnnotation.toString().substring(1, predictedAnnotation.length()));
                            predictedAnnotation.delete(0, predictedAnnotation.length());
                        }
                    } else {
                        if (correctAnnotation.length() > 0) {
                            correctAnnotationList.add(correctAnnotation.toString().substring(1, correctAnnotation.length()));
                            correctAnnotation.delete(0, correctAnnotation.length());
                        }
                        if (predictedAnnotation.length() > 0) {
                            predictedAnnotationList.add(predictedAnnotation.toString().substring(1, predictedAnnotation.length()));
                            predictedAnnotation.delete(0, predictedAnnotation.length());
                        }
                    }


                    line = br.readLine();
                }

                getMinimumPenalty(correctAnnotationList, predictedAnnotationList, 3, 2);
                correctAnnotationList.clear();
                predictedAnnotationList.clear();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // sequence alignment: it aligns predicted and correct named entities
    public static void getMinimumPenalty(List<String> x, List<String> y, int pxy, int pgap) {
        int i, j; // intialising variables

        int m = x.size(); // length of gene1
        int n = y.size(); // length of gene2

        // table for storing optimal
        // substructure answers
        int dp[][] = new int[n + m + 1][n + m + 1];

        for (int[] x1 : dp)
            Arrays.fill(x1, 0);

        // intialising the table
        for (i = 0; i <= (n + m); i++)
        {
            dp[i][0] = i * pgap;
            dp[0][i] = i * pgap;
        }

        // calcuting the
        // minimum penalty
        for (i = 1; i <= m; i++)
        {
            for (j = 1; j <= n; j++)
            {
                if (x.get(i - 1).equals( y.get(j - 1)))
                {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else
                {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + pxy ,
                            dp[i - 1][j] + pgap) ,
                            dp[i][j - 1] + pgap );
                }
            }
        }

        // Reconstructing the solution
        int l = n + m; // maximum possible length

        i = m; j = n;

        int xpos = l;
        int ypos = l;

        // Final answers for
        // the respective strings
        String xans[] = new String[l + 1];
        String yans[] = new String[l + 1];

        while ( !(i == 0 || j == 0))
        {
            if (x.get(i - 1).equals( y.get(j - 1)))
            {
                xans[xpos--] = x.get(i - 1);
                yans[ypos--] = y.get(j - 1);
                i--; j--;
            }
            else if (dp[i - 1][j - 1] + pxy == dp[i][j])
            {
                xans[xpos--] = x.get(i - 1);
                yans[ypos--] = y.get(j - 1);
                i--; j--;
            }
            else if (dp[i - 1][j] + pgap == dp[i][j])
            {
                xans[xpos--] = x.get(i - 1);
                yans[ypos--] = "_";
                i--;
            }
            else if (dp[i][j - 1] + pgap == dp[i][j])
            {
                xans[xpos--] = "_";
                yans[ypos--] = y.get(j - 1);
                j--;
            }
        }
        while (xpos > 0)
        {
            if (i > 0) xans[xpos--] = x.get(--i);
            else xans[xpos--] = "_";
        }
        while (ypos > 0)
        {
            if (j > 0) yans[ypos--] = y.get(--j);
            else yans[ypos--] = "_";
        }

        // Since we have assumed the
        // answer to be n+m long,
        // we need to remove the extra
        // gaps in the starting id
        // represents the index from
        // which the arrays xans,
        // yans are useful
        int id = 1;
        for (i = l; i >= 1; i--)
        {
            if (yans[i].equals("_") &&
                    xans[i].equals("_"))
            {
                id = i + 1;
                break;
            }
        }

        // Printing the final answer
        int correctlyClassified = 0;
        for (i = id; i <= l; i++) {
            if (xans[i].equals(yans[i])) {
                correctlyClassified++;
            }
        }
        double precision = correctlyClassified*1.0/y.size();
        double recall = correctlyClassified*1.0/x.size();
        System.out.println("Precision: " + precision + "\nRecall: " + recall + "\nSupport: " + x.size());
        return;
    }

    public static void main(String[] args) {
         coNllF1Score("predictions.txt");
    }
}
