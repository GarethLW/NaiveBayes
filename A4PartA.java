import java.io.*;
import java.util.*;

public class A4PartA
{
    private static FreqTable freqTable = null;
    private static final boolean ISSPAM = true;
    private static double spamMistakes = 0;
    private static int spamTotalTests = 0;
    private static double hamMistakes = 0;
    private static int hamTotalTests = 0;

    // for header
    private static FreqTable headerTable = new FreqTable();

    // options

    // if true it uses trigrams otherwise it uses words.
    // this does not affect how header data attributes
    // are handled
    private static final boolean USE_TRIGRAM = false;
    private static final boolean USE_HEADER_DATA = true;
    // uses an ordered list based on how frequent
    // a word shows up in both spam and ham emails if true.
    // if false it uses non ordered data structure
    private static final boolean USE_ORDERED_TABLE = false;
    
    public static void main(String args[])
    {
        long startTime = 0;
        long endTime = 0;

        if(USE_ORDERED_TABLE) freqTable = new FreqTableOrdered();
        else freqTable = new FreqTable();

        // training files
        File trainingHam = new File(".\\training\\ham\\");
        File trainingSpam = new File(".\\training\\spam\\"); // ".\\CSDMC2010_SPAM\\spamTrain\\"

        // testing files
        File testingHam = new File(".\\testing\\ham\\");
        File testingSpam = new File(".\\testing\\spam\\"); // ".\\CSDMC2010_SPAM\\spamTest\\"

        train(trainingHam, trainingSpam);

        startTime = System.nanoTime();
        test(testingHam, testingSpam);
        endTime = System.nanoTime();

        System.out.println("Total testsing elapsed time: "+ (endTime-startTime));

        System.out.println("\nResults:");

        // test report for ham files
        System.out.println("\nHam tests report from file: "+testingHam);
        System.out.println("\tCorrectly classified: "+(hamTotalTests - hamMistakes)+" (emails)");
        System.out.println("\tIncorrectly classified: "+hamMistakes+" (emails)");
        System.out.println("\tAccuracy: %"+ 100*(1 - (hamMistakes/hamTotalTests)));
        System.out.println("\tError rate: %"+ 100*(hamMistakes/hamTotalTests));

        // test report for spam files
        System.out.println("\nSpam tests report from file: "+testingSpam);
        System.out.println("\tCorrectly classified: "+(spamTotalTests - spamMistakes)+" (emails)");
        System.out.println("\tIncorrectly classified: "+spamMistakes+" (emails)");
        System.out.println("\tAccuracy: %"+ 100*(1 - (spamMistakes/spamTotalTests)));
        System.out.println("\tError rate: %"+ 100*(spamMistakes/spamTotalTests));

        System.out.println("\nProgram ends\n");
    }

    public static void test(File ham, File spam)
    {
        System.out.println("\nTesting ham:");
        testFrom(ham, !ISSPAM);
        System.out.println("\nTesting spam:");
        testFrom(spam, ISSPAM);
    }

    public static void testFrom(File file, boolean isSpam)
    {
        File[] listOfFiles = file.listFiles();
        boolean probSpam = false;
        boolean printFirst = true;
        int totalTests = listOfFiles.length;
        double mistakes = 0;

        // train by looping through all files in file
        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                // print the first email
                if(printFirst)
                {
                    printFirst = false;
                    printFile(file+"\\"+listOfFiles[i].getName());
                }

                // training progress
                if(i%10==0)System.out.print("Testing emails from dir: " + file + ", File: "+i+" / "+listOfFiles.length+"\r");

                // read all emails from: training/file
                probSpam = testEmail(file+"\\"+listOfFiles[i].getName(), isSpam);
                if (probSpam != isSpam) mistakes++;
            } 
        }
        System.out.println("Testing on emails from dir: " + file + " - Done               ");
        if(isSpam)
        {
            spamMistakes = mistakes;
            spamTotalTests = totalTests;
        }
        else
        {
            hamMistakes = mistakes;
            hamTotalTests = totalTests;
        }
    }

    public static void train(File ham, File spam)
    {
        System.out.println("Training ham:");
        trainFrom(ham, !ISSPAM);
        System.out.println("Training spam:");
        trainFrom(spam, ISSPAM);
    }

    public static void trainFrom(File file, boolean isSpam)
    {
        boolean printFirst = true;
        File[] listOfFiles = file.listFiles();

        // train by looping through all files in file
        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
              // read all emails from: training/file
              readEmail(file+"\\"+listOfFiles[i].getName(), isSpam);
              if(printFirst)
              {
                  printFirst = false;
                  printFile(file+"\\"+listOfFiles[i].getName());
              }

              // training progress
              if(i%10==0)System.out.print("Training on emails from dir: " + file + ", File: "+i+" / "+listOfFiles.length+"\r");
            } 
        }
        System.out.println("Training on emails from dir: " + file + " - Done               ");
    }

    public static void readEmail(String email, boolean isSpam)
    {
        try
        {     
            FileReader file = new FileReader(email);
            BufferedReader reader = new BufferedReader(file);
            
            // file variables
            String line; 
            int lineNum = 0;

            // tokenizing variables
            boolean foundEmail = false;
            char[] trigram = new char[3];
            StringTokenizer st = null;
            String wordToAdd = null;

            // header variables
            String temp = null;
        
            // read line
            for(lineNum = 0; (line = reader.readLine()) != null; lineNum++)
            {
                if (foundEmail) 
                {
                    st = new StringTokenizer(line);
                    // tokenize the words in the line
                    while (st.hasMoreTokens()) 
                    {
                        wordToAdd = stemThis( st.nextToken() );
                        if (USE_TRIGRAM)
                        {
                            // loop through the word and make trigrams
                            // if the word is less than three charachters then ignore it
                            if (wordToAdd.length() > 2)
                            {
                                for(int i = 0; i < wordToAdd.length()-2; i++)
                                {
                                    if (i+2 < wordToAdd.length())
                                    {
                                        trigram[0] = wordToAdd.charAt(i);
                                        trigram[1] = wordToAdd.charAt(i+1);
                                        trigram[2] = wordToAdd.charAt(i+2);
                                        freqTable.addAttribute(new String(trigram), isSpam);
                                    }
                                }
                            }
                        }
                        // else use word
                        else freqTable.addAttribute( wordToAdd, isSpam );
                    }
                }
                // find where the header ends
                // assumes the first empty line is the end of the header
                else if (line.length() == 0) foundEmail = true;

                // In header
                else if (USE_HEADER_DATA)
                {
                    if (line.startsWith("Subject: "))
                    {
                        // tokenize the line
                        st = new StringTokenizer(line);
                        // drop the first token since it is Subject:
                        if(st.hasMoreTokens()) st.nextToken();
                        // tokenize the words in the line
                        while (st.hasMoreTokens()) 
                        {
                            temp = st.nextToken();
                            
                            wordToAdd = temp;//stemThis( temp );

                            headerTable.addAttribute( wordToAdd, isSpam );

                        }
                    }
                }
            }
            file.close();
        } //try
        catch(IOException e)
        {
          System.out.println(e);
        }
    }

    // returns the proportion of upper cases in a string
    public static float countCaseProp(String str)
    {
        float upper = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isUpperCase(str.charAt(i))) upper++;
        }
        return upper/str.length();
    }

    public static boolean testEmail(String email, boolean spam)
    {
        boolean probSpam = true;
        try
        {     
            FileReader file = new FileReader(email);
            BufferedReader reader = new BufferedReader(file);
            
            // file variables
            String line; 
            int lineNum = 0;
            boolean foundEmail = false;
            boolean done = false;

            // testing variables
            RowData data = null;
            double givenSpam = 1;
            double givenHam = 1;
            StringTokenizer st = null;
            char[] trigram = new char[3];
            String currWord = null;

            // header variables
            String temp = null;
            String wordToGet = null;
        
            // read line
            for(lineNum = 0; (line = reader.readLine()) != null; lineNum++)
            {
                if (foundEmail) 
                {
                    st = new StringTokenizer(line);
                    // tokenize the line
                    while (st.hasMoreTokens() && !done) 
                    {
                        // find table data for this word
                        currWord = stemThis(st.nextToken());

                        if (USE_TRIGRAM)
                        {
                            // loop through the word and make trigrams
                            // if the word is less than three charachters then ignore it
                            if (currWord.length() > 2)
                            {
                                for(int i = 0; i < currWord.length()-2 && !done; i++)
                                {
                                    if (i+2 < currWord.length())
                                    {
                                        trigram[0] = currWord.charAt(i);
                                        trigram[1] = currWord.charAt(i+1);
                                        trigram[2] = currWord.charAt(i+2);

                                        data = freqTable.getAttribute( new String(trigram) );
                                        if (data != null)
                                        {
                                            givenHam += probability(data, false);
                                            givenSpam += probability(data, true);
            
                                            // if the accuracy is to low, stop.
                                            if (Double.isInfinite(givenHam)) done = true;
                                            else if (Double.isInfinite(givenSpam)) done = true;
                                        }
                                    }
                                }
                            }
                        }
                        
                        else
                        {
                            data = freqTable.getAttribute( currWord );
                            if (data != null)
                            {
                                givenHam += probability(data, false);
                                givenSpam += probability(data, true);

                                // if the accuracy is to low, stop.
                                if (Double.isInfinite(givenHam)) done = true;
                                else if (Double.isInfinite(givenSpam)) done = true;
                            }
                        }
                    }
                }
                else if (line.length() == 0) foundEmail = true;

                // In header
                else if (USE_HEADER_DATA)
                {
                    if (line.startsWith("Subject: "))
                    {
                        // count upper cases
                        // P(uppercase|ham) < 100%
                        //if (countCaseProp(line) > 0.8) givenHam+=0.03;

                        // tokenize the line
                        st = new StringTokenizer(line);
                        // drop the first token since it is Subject:
                        if(st.hasMoreTokens()) st.nextToken();
                        // tokenize the words in the line
                        while (st.hasMoreTokens()) 
                        {
                            temp = st.nextToken();
                            // count upper cases

                            wordToGet = temp;//stemThis( temp );

                            data = headerTable.getAttribute( wordToGet );

                            if (data != null)
                            {
                                givenHam += probability(data, false);
                                givenSpam += probability(data, true);

                                // if the accuracy is to low, stop.
                                if (Double.isInfinite(givenHam)) done = true;
                                else if (Double.isInfinite(givenSpam)) done = true;
                            }

                        }
                    }
                }
            }
            file.close();

            // Determine whether the correct decision was made
            if (Math.abs(givenHam) < Math.abs(givenSpam)) 
            {
                probSpam = false;
            }
        } //try
        catch(IOException e)
        {
          System.out.println(e);
        }
        return probSpam;
    }

    public static double probability(RowData data, boolean isSpam)
    {
        // assuming the attributes are conditionally independent
        // p(spam,a1,a2,...,an) vs p(ham,a1,a2,...,an)
        // p(a1|spam)...p(an|spam)p(spam) vs p(a1|ham)...p(an|ham)p(ham)
        int k = freqTable.getTotalUWords();

        double output = 0;

        if(!isSpam) output = Math.log10(( (double)data.getHamCount() +(1/(double)k)) / (freqTable.getTotalHam()+1));
        else  output = Math.log10(( (double)data.getSpamCount() +(1/(double)k)) / (freqTable.getTotalSpam()+1));

        return output;
    }

    public static String stemThis (String inputWord)
    {
        char [] word = inputWord.toCharArray();
        char temp = ' ';
        Stemmer stemmer = new Stemmer();
        for(int i = 0; i < word.length; i++) 
        {
            // to lower case
            temp = Character.toLowerCase(word[i]);

            // add it if its a letter or digit
            if(Character.isLetterOrDigit(temp)) stemmer.add(temp);
        }
        stemmer.stem();
        return stemmer.toString();
    }

    // print the email to stdout
    public static void printFile(String email)
    {
        int lineNum = 0;
        String line = "";
        boolean foundEmail = false;

        System.out.println("\n**********************************************************************************************");
        System.out.println("Printing Email...:\n");
        try
        {     
            FileReader file = new FileReader(email);
            BufferedReader reader = new BufferedReader(file);
 
            // read line
            for(lineNum = 0; (line = reader.readLine()) != null; lineNum++)
            {
                if (foundEmail) System.out.println(line);
                else if (line.length() == 0) foundEmail = true;
            }
            file.close();
            System.out.println("\nEmail ends...");
            System.out.println("**********************************************************************************************\n");
        } //try
        catch(IOException e)
        {
          System.out.println(e);
        }
    }
}