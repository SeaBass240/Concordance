/*
 * Sam S-N
 * Main Concordance class that parses and stores information about the count and location of words in a text file
 * Created: 6/01/2023
 * Modified: 6/01/2023
 */
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Concordance {
    private static MyListMap<String, ArrayList<String>> map = new MyListMap<>();
    private static AVLTree<KeyValuePair<String,Integer>> tree = new AVLTree<>();
    private static Scanner inData;
    private static int lineNum = 0;
    private static int wordNum = 0;

    public Concordance(String filename) {
        FileReader reader;
        inData = null;
        //Read and parse the included .txt file.
        try {
            reader = new FileReader(filename);
            inData = new Scanner(reader);
        } catch (Exception e) {
            System.out.println("Opening input file failed: " + e);
            System.exit(0);
        }
        parseFile();
    }

    public void parseFile(){
        lineNum = 0;
        //Read each line of the file
        while (inData.hasNextLine()) {
            lineNum++;
            String[] line = inData.nextLine().split(" ");
            wordNum = 0;
            //Read each word of the line
            for (String word : line) {
                //Map
                addToMap(word);


                //Tree
                addToTree(word);
                wordNum++;
            }

        }
    }

    public void addToTree(String word){
        if (!tree.insert(new KeyValuePair<>(word, 1))) {
            //Get number of previous tree node
            int number = tree.findData(new KeyValuePair<>(word, null)).getSecond();
            //Update tree node
            tree.set(new KeyValuePair<>(word, null),
                    new KeyValuePair<>(word, number + 1));
        }
    }

    public void addToMap(String word){
        map.put(word, new ArrayList<>());
        //Add location of word to the map list
        try {
            ArrayList<String> list = map.get(word);
            list.add(lineNum + ":" + wordNum);
            map.set(word, list);
        } catch (KeyError e) {
            System.out.println("KeyError: " + e);
        }

    }

    public AVLTree<KeyValuePair<String,Integer>> getTree(){
        return tree;
    }

    public MyListMap<String, ArrayList<String>> getMap(){
        return map;
    }


}
