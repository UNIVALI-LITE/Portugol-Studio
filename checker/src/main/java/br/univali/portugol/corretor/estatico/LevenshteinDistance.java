package br.univali.portugol.corretor.estatico;

public class LevenshteinDistance {
    
    public static int execute(String a, String b) {
        
        char[] arrayA = a.toCharArray();
        char[] arrayB = b.toCharArray();
        
        int[][] levenshtein = new int[a.length()][b.length()];
        
        for (int i = 0; i < arrayA.length; i ++){
            levenshtein[i][0] = i;
        }
        
        for (int j = 0; j < arrayB.length; j ++){
            levenshtein[0][j] = j;
        }
        
        for (int i = 1; i < levenshtein.length; i ++) {
        
            for (int j = 1; j < levenshtein[i].length; j ++) {
            
                if (arrayA[i] == arrayB[j]) {
                    levenshtein[i][j] = levenshtein[i - 1][j - 1];
                } else {
                    levenshtein[i][j] = 
                            Math.min(levenshtein[i - 1][j] + 1,Math.min(levenshtein[i][j-1]+1,levenshtein[i - 1][j - 1] + 1));
                }
            
            }
        
        }
        
        return levenshtein[arrayA.length - 1][arrayB.length - 1];
    }
        
}
