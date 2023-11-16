import java.util.PriorityQueue;
import java.util.HashMap;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }
}

public class Huffman {

    public static void main(String[] args) {
        String inputString = "huffman coding example";

        // Build the Huffman tree
        HuffmanNode root = buildHuffmanTree(inputString);

        // Build the Huffman codes
        HashMap<Character, String> huffmanCodes = generateHuffmanCodes(root, "");

        // Print the Huffman codes
        System.out.println("Huffman Codes:");
        for (char key : huffmanCodes.keySet()) {
            System.out.println(key + ": " + huffmanCodes.get(key));
        }

        // Encode the input string
        String encodedString = encodeString(inputString, huffmanCodes);
        System.out.println("Encoded String: " + encodedString);

        // Decode the encoded string
        String decodedString = decodeString(encodedString, root);
        System.out.println("Decoded String: " + decodedString);
    }

    private static HuffmanNode buildHuffmanTree(String input) {
        HashMap<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        for (char key : frequencyMap.keySet()) {
            priorityQueue.offer(new HuffmanNode(key, frequencyMap.get(key)));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode newNode = new HuffmanNode('\0', left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;

            priorityQueue.offer(newNode);
        }

        return priorityQueue.poll();
    }

    private static HashMap<Character, String> generateHuffmanCodes(HuffmanNode root, String code) {
        HashMap<Character, String> huffmanCodes = new HashMap<>();

        generateCodes(root, code, huffmanCodes);

        return huffmanCodes;
    }

    private static void generateCodes(HuffmanNode node, String code, HashMap<Character, String> huffmanCodes) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                huffmanCodes.put(node.data, code);
            }

            generateCodes(node.left, code + "0", huffmanCodes);
            generateCodes(node.right, code + "1", huffmanCodes);
        }
    }

    private static String encodeString(String input, HashMap<Character, String> huffmanCodes) {
        StringBuilder encodedString = new StringBuilder();

        for (char c : input.toCharArray()) {
            encodedString.append(huffmanCodes.get(c));
        }

        return encodedString.toString();
    }

    private static String decodeString(String encodedString, HuffmanNode root) {
        StringBuilder decodedString = new StringBuilder();

        HuffmanNode current = root;
        for (char bit : encodedString.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                decodedString.append(current.data);
                current = root;
            }
        }

        return decodedString.toString();
    }
}
