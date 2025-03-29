package huffman;



import com.sun.source.tree.Tree;
import huffman.tree.Branch;
import huffman.tree.Leaf;
import huffman.tree.Node;


import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.*;

import static org.junit.Assert.assertNull;

/**
 * The class implementing the Huffman coding algorithm.
 */
public class Huffman {


    /**
     * Build the frequency table containing the unique characters from the String `input' and the number of times
     * that character occurs.
     *
     * @param input The string.
     * @return The frequency table.
     */
    public static Map<Character, Integer> freqTable(String input) {
        if (input == null || input.isEmpty()) {  // If users input is empty, then it will return null
            return null;
        }
        else {
            Map<Character, Integer> ft = new HashMap<>();   // Creates an empty Hashmap using the data type of Character as the key of labels and the Integer as its value for frequency.
            for (int i = 0; i < input.length(); i++) {   // Loops for n number of characters from the users input.
                char c = input.charAt(i);                // Assign the variable c to every character in the string for each loop made.
                if (ft.containsKey(c)) {                 // Identifies if the current character existed in the HashMap.
                    ft.put(c, ft.get(c) + 1);            // If this is true, then it adds 1 to the value (i.e Frequency) of the Character in the HashMap.
                } else {
                    ft.put(c, 1);           // If this character is not found, then it adds the character to the HashMap and sets its Value to 1.
                }

            }
            return ft;                  // After the loop has been completed, the HashMap is returned.
        }
    }


    /**
     * Given a frequency table, construct a Huffman tree.
     * <p>
     * First, create an empty priority queue.
     * <p>
     * Then make every entry in the frequency table into a leaf node and add it to the queue.
     * <p>
     * Then, take the first two nodes from the queue and combine them in a branch node that is
     * labelled by the combined frequency of the nodes and put it back in the queue. The right hand
     * child of the new branch node should be the node with the larger frequency of the two.
     * <p>
     * Do this repeatedly until there is a single node in the queue, which is the Huffman tree.
     *
     * @param freqTable The frequency table.
     * @return A Huffman tree.
     */
    public static Node treeFromFreqTable(Map<Character, Integer> freqTable) {
        PQueue pq = new PQueue();                               // Creates a new priority Queue to store the Characters and Integers in the Hashtable.
        for (Character i : freqTable.keySet()) {                // it will loop for each Character to Value
            Leaf newleaf = new Leaf(i, freqTable.get(i));       //creates a new lead and assigns it to the variable "Newleaf"
            pq.enqueue(newleaf);                                   // adds the leaf to the priority queue
        }

        while (pq.size() > 1) {                                 // Loops until the priority queue quantity is greater than 1
            Node a = pq.dequeue();                              // Removes the first leaf of the Queue
            Node b = pq.dequeue();                              // Removes the following leaf that is positioned as first in the queue.
            Node parent = new Branch(a.getFreq() + b.getFreq(), b, a);  // This variable creates a Branch. It combines the two nodes frequency and sets the left node as Node B and sets the right node as Node A
            pq.enqueue(parent); // adds the Branch to the tree
        }
        return pq.dequeue(); // Returns the tree by taking the first Node in the queue each time.
    }


    /**
     * Construct the map of characters and codes from a tree. Just call the traverse
     * method of the tree passing in an empty list, then return the populated code map.
     *
     * @param tree The Huffman tree.
     * @return The populated map, where each key is a character, c, that maps to a list of booleans
     * representing the path through the tree from the root to the leaf node labelled c.
     */
    public static Map<Character, List<Boolean>> buildCode(Node tree) {
        ArrayList<Boolean> codes = new ArrayList<Boolean>(); // Creates an empty array
        return tree.traverse(codes);    // traverse through the code arraylist
    }


    /**
     * Create the huffman coding for an input string by calling the various methods written above. I.e.
     * <p>
     * + create the frequency table,
     * + use that to create the Huffman tree,
     * + extract the code map of characters and their codes from the tree.
     * <p>
     * Then to encode the input data, loop through the input looking each character in the map and add
     * the code for that character to a list representing the data.
     *
     * @param input The data to encode.
     * @return The Huffman coding.
     */
    public static HuffmanCoding encode(String input) {
        Map<Character, Integer> frequencyTable = freqTable(input);  // Creates the frequency table using the freqTable method
        Node huffmanTree = treeFromFreqTable(frequencyTable);          // Creates a tree using the frequency table created in the frequencyTable variable
        Map<Character, List<Boolean>> MapCode = buildCode(huffmanTree);    // Extracts the code map of character and their codes from the tree
        ArrayList<Boolean> encodeList = new ArrayList<Boolean>();       // Creates encodeList arraylist to input the characters and their nodes
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);                                   // Loops through each character from the input
            if (MapCode.containsKey(c)) {                               // Checks if the Character exists in the HashMap MapCode
                encodeList.addAll(MapCode.get(c));                      // Adds the character to the list
            }
        }
        return new HuffmanCoding(MapCode, encodeList);      // returns the character and the list of code.
    }


    /**
     * Reconstruct a Huffman tree from the map of characters and their codes. Only the structure of this tree
     * is required and frequency labels of all nodes can be set to zero.
     * <p>
     * Your tree will start as a single Branch node with null children.
     * <p>
     * Then for each character key in the code, c, take the list of booleans, bs, corresponding to c. Make
     * a local variable referring to the root of the tree. For every boolean, b, in bs, if b is false you want to "go
     * left" in the tree, otherwise "go right".
     * <p>
     * Presume b is false, so you want to go left. So long as you are not at the end of the code so you should set the
     * current node to be the left-hand child of the node you are currently on. If that child does not
     * yet exist (i.e. it is null) you need to add a new branch node there first. Then carry on with the next entry in
     * bs. Reverse the logic of this if b is true.
     * <p>
     * When you have reached the end of this code (i.e. b is the final element in bs), add a leaf node
     * labelled by c as the left-hand child of the current node (right-hand if b is true). Then take the next char from
     * the code and repeat the process, starting again at the root of the tree.
     *
     * @param code The code.
     * @return The reconstructed tree.
     */
    public static Node treeFromCode(Map<Character, List<Boolean>> code) {
        Branch root = new Branch(0, null, null); // It creates a new branch node with null children
        Set<Character> chars = code.keySet();
        for (char c : chars) { //for each character key in the code, c, take the list of booleans, bs, corresponding to c
            Node currentNode = root;   //Setting currentNode to the root variable
            Iterator<Boolean> bs = code.get(c).iterator();  // Setting the iterator to list of codes
            while (bs.hasNext()) {  // Loops till end of the code list
                boolean b = bs.next(); //Sets the variable b to every item of the list of codes
                if (!b) {  // if b is false
                    if (!bs.hasNext()) {
                        ((Branch) currentNode).setLeft(new Leaf(c, 0));
                        //if the iterator does not have a next code, it will go left
                        currentNode = ((Branch) currentNode).getLeft();
                    } else if (((Branch) currentNode).getLeft() == null) {   // If branch does not exist and is null, then it creates a new branch
                        ((Branch) currentNode).setLeft(new Branch(0, null, null));
                        currentNode = ((Branch) currentNode).getLeft();
                    } else {
                        //Node will go left
                        currentNode = ((Branch) currentNode).getLeft();
                    }
                } else { //if 'b' is 'true'
                    if (!bs.hasNext()) {
                        ((Branch) currentNode).setRight(new Leaf(c, 0));
                        //if the iterator does not have a next code, it will go right
                        currentNode = ((Branch) currentNode).getRight();
                    } else if (((Branch) currentNode).getRight() == null) { // If branch does not exist and is null, then it creates a new branch
                        ((Branch) currentNode).setRight(new Branch(0, null, null));
                        currentNode = ((Branch) currentNode).getRight();
                    } else {
                        //Node will go right
                        currentNode = ((Branch) currentNode).getRight();
                    }
                }
            }
        }
        return root;
    }

    /**
     * Decode some data using a map of characters and their codes. To do this you need to reconstruct the tree from the
     * code using the method you wrote to do this. Then take one boolean at a time from the data and use it to traverse
     * the tree by going left for false, right for true. Every time you reach a leaf you have decoded a single
     * character (the label of the leaf). Add it to the result and return to the root of the tree. Keep going in this
     * way until you reach the end of the data.
     *
     * @param code The code.
     * @param data The encoded data.
     * @return The decoded string.
     */
    public static String decode(Map<Character, List<Boolean>> code, List<Boolean> data) {
        String decodedString = ""; // Initial set variable
        Branch root = (Branch)treeFromCode(code); //Implements treeFromCode method with the Branch Class
        Node currentNode = root; // Sets CurrentNode to root
        for (Boolean b : data) {       // For each item in the List data
            if(currentNode instanceof Branch) { // Checks whether Branch is present in currentNode
                if (b) {                            //If b equals to true then currentNode gets right
                    currentNode = ((Branch) currentNode).getRight();
                } else {                            ////else the currentNode gets left
                    currentNode = ((Branch) currentNode).getLeft();
                }
            }
                if (currentNode instanceof Leaf) { // Checks if Leaf is present in currentNode
                    decodedString += Character.toString(((Leaf) currentNode).getLabel()); //Gets the Label of the current Node and assigns to the string variable
                    currentNode = root; //current node set back to root for its next loop
                }
            }
                System.out.println(decodedString); //returns the label
                return decodedString;
            }
}