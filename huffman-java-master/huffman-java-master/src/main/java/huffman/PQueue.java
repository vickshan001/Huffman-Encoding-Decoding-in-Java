package huffman;

import huffman.tree.Node;

import java.util.ArrayList;
import java.util.List;
/**
 * A priority queue of @Node@ objects. Each node has an int as its label representing its frequency.
 * The queue should order objects in ascending order of frequency, i.e. lowest first.
 */
public class PQueue {

    private List<Node> queue;

    public PQueue() {
        queue = new ArrayList<>();
    }

    /**
     * Add a node to the queue. The new node should be inserted at the point where the frequency of next node is
     * greater than or equal to that of the new one.
     * @param n The node to enqueue.
     */
    public void enqueue(Node n) {
        boolean done = false;                // Sets a instance variable to false
        for (int i = 0; i< queue.size(); i++){  // it loops n number of nodes in the queue
                Node current = queue.get(i);  // Sets the variable current to the current Node in the loop
                if (n.getFreq() <= current.getFreq()){   // if the "n" Nodes frequency is less than or equal to the current nodes frequency, it creates a node that adds current loop (Variable i) and the node "n" to the queue
                    queue.add(i, n);
                    done = true;
                    break;
                }
        }
        if (done != true){
            queue.add(queue.size(),n); // adds the n number nodes in the queue and the Node n in the queue
        }
    }

    /**
     * Remove a node from the queue.
     * @return  The first node in the queue.
     */
    public Node dequeue() {
        if (queue.size() == 0){    // Checks if queue is empty based on the number of nodes in the queue
            return null;            // if it is empty, then it returns null
        }else {
            return queue.remove(0);     // else it will remove and return the first node in the queue
        }
    }

    /**
     * Return the size of the queue.
     * @return  Size of the queue.
     */
    public int size() {
        return queue.size();
    }  // Returns the number of nodes in the queue.
}
