package Trees;

class BST{
    // Node class for BST
    class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    Node root;
    int size;

    BST() {
        this.root = null;
        this.size = 0;
    }

    // Insert a new element (BST property: left < parent < right)
    void insert(int value) {
        root = insertRecursive(root, value);
        size++;
    }

    private Node insertRecursive(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = insertRecursive(node.right, value);
        }
        // If equal, we can either ignore or handle differently
        // Here we'll just ignore duplicates (or you could count frequency)

        return node;
    }

    // Search for a value
    boolean search(int value) {
        return searchRecursive(root, value);
    }

    private boolean searchRecursive(Node node, int value) {
        if (node == null) {
            return false;
        }

        if (value == node.value) {
            return true;
        } else if (value < node.value) {
            return searchRecursive(node.left, value);
        } else {
            return searchRecursive(node.right, value);
        }
    }

    // Find minimum value
    int findMin() {
        if (root == null) {
            System.out.println("ERROR: Tree is empty!");
            return -1;
        }

        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    // Find maximum value
    int findMax() {
        if (root == null) {
            System.out.println("ERROR: Tree is empty!");
            return -1;
        }

        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    // Delete a value
    void delete(int value) {
        root = deleteRecursive(root, value);
        if (root != null || size > 0) {
            size--;
        }
    }

    private Node deleteRecursive(Node node, int value) {
        if (node == null) {
            System.out.println("ERROR: Value not found!");
            return null;
        }

        if (value < node.value) {
            node.left = deleteRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = deleteRecursive(node.right, value);
        } else {
            // Node to be deleted found

            // Case 1: No child or one child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Case 2: Two children
            // Find inorder successor (minimum in right subtree)
            Node successor = findMinNode(node.right);
            node.value = successor.value;

            // Delete the successor
            node.right = deleteRecursive(node.right, successor.value);
        }

        return node;
    }

    void UpdateValue(int oldValue, int newValue){
        if (!search(oldValue)){
            System.out.println("Value not found!"); return;
        }
        if (search(newValue)){
            System.out.println("Value already exists!"); return;
        }

        delete(oldValue);
        insert(newValue);


    }

    private Node findMinNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Get root value (similar to getMax in heap)
    int getRoot() {
        if (root == null) {
            System.out.println("ERROR: Tree is empty!");
            return -1;
        }
        return root.value;
    }


    // Helper method to find index (returns -1 if not found)
    int findIndex(int value) {
        return findIndexRecursive(root, value, 1);
    }

    private int findIndexRecursive(Node node, int value, int position) {
        if (node == null) {
            return -1;
        }

        if (value == node.value) {
            return position;
        }

        int leftIndex = findIndexRecursive(node.left, value, position * 2);
        if (leftIndex != -1) return leftIndex;

        return findIndexRecursive(node.right, value, position * 2 + 1);
    }

    // Get parent value
    int getParentValue(int value) {
        if (root == null || root.value == value) {
            System.out.println("Node is root or not found");
            return -1;
        }
        return getParentValueRecursive(root, value);
    }

    private int getParentValueRecursive(Node node, int value) {
        if (node == null) return -1;

        if ((node.left != null && node.left.value == value) ||
                (node.right != null && node.right.value == value)) {
            return node.value;
        }

        if (value < node.value) {
            return getParentValueRecursive(node.left, value);
        } else {
            return getParentValueRecursive(node.right, value);
        }
    }

    // Check if node is leaf
    boolean isLeaf(int value) {
        Node node = findNode(root, value);
        if (node == null) {
            System.out.println("Value not found!");
            return false;
        }
        return node.left == null && node.right == null;
    }

    private Node findNode(Node node, int value) {
        if (node == null || node.value == value) {
            return node;
        }

        if (value < node.value) {
            return findNode(node.left, value);
        } else {
            return findNode(node.right, value);
        }
    }

    // Get height of tree
    int getHeight() {
        return getHeightRecursive(root);
    }

    private int getHeightRecursive(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeightRecursive(node.left),
                getHeightRecursive(node.right));
    }

    // Check if tree is complete (BST doesn't guarantee completeness)
    boolean isComplete() {
        return isCompleteRecursive(root, 0, countNodes(root));
    }

    private boolean isCompleteRecursive(Node node, int index, int totalNodes) {
        if (node == null) return true;

        if (index >= totalNodes) return false;

        return isCompleteRecursive(node.left, 2 * index + 1, totalNodes) &&
                isCompleteRecursive(node.right, 2 * index + 2, totalNodes);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // Check if BST property is satisfied
    boolean isBSTPropertySatisfied() {
        return isBSTPropertySatisfiedRecursive(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTPropertySatisfiedRecursive(Node node, int min, int max) {
        if (node == null) return true;

        if (node.value < min || node.value > max) return false;

        return isBSTPropertySatisfiedRecursive(node.left, min, node.value - 1) &&
                isBSTPropertySatisfiedRecursive(node.right, node.value + 1, max);
    }

    // Display tree as array (level order)
    void printArray() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        int height = getHeight();
        int maxNodes = (int) Math.pow(2, height) - 1;
        Integer[] levelOrder = new Integer[maxNodes];

        printArrayRecursive(root, 0, levelOrder);

        System.out.print("BST array (level order): ");
        for (int i = 0; i < maxNodes; i++) {
            if (levelOrder[i] != null) {
                System.out.print(levelOrder[i] + " ");
            }
        }
        System.out.println();
        System.out.println("Size: " + size);
    }

    private void printArrayRecursive(Node node, int index, Integer[] levelOrder) {
        if (node == null || index >= levelOrder.length) return;

        levelOrder[index] = node.value;
        printArrayRecursive(node.left, 2 * index + 1, levelOrder);
        printArrayRecursive(node.right, 2 * index + 2, levelOrder);
    }

    // Display tree level by level
    void printTree() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        int height = getHeight();
        for (int level = 1; level <= height; level++) {
            printLevel(root, level);
            System.out.println();
        }
    }

    private void printLevel(Node node, int level) {
        if (node == null) {
            System.out.print("_ ");
            return;
        }

        if (level == 1) {
            System.out.print(node.value + " ");
        } else {
            printLevel(node.left, level - 1);
            printLevel(node.right, level - 1);
        }
    }

    // Display tree with indentation (tree structure)
    void printTreeStructure() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        printTreeStructureRecursive(root, "", true);
    }

    private void printTreeStructureRecursive(Node node, String prefix, boolean isLast) {
        if (node == null) return;

        System.out.print(prefix);
        System.out.print(isLast ? "└── " : "├── ");
        System.out.println(node.value);

        String childPrefix = prefix + (isLast ? "    " : "│   ");

        if (node.left != null && node.right != null) {
            printTreeStructureRecursive(node.left, childPrefix, false);
            printTreeStructureRecursive(node.right, childPrefix, true);
        } else if (node.left != null) {
            printTreeStructureRecursive(node.left, childPrefix, true);
        } else if (node.right != null) {
            printTreeStructureRecursive(node.right, childPrefix, true);
        }
    }

    // Traversals
    void inorderTraversal() {
        System.out.print("In-order Traversal: ");
        inorderTraversalRecursive(root);
        System.out.println();
    }

    private void inorderTraversalRecursive(Node node) {
        if (node == null) return;
        inorderTraversalRecursive(node.left);
        System.out.print(node.value + " ");
        inorderTraversalRecursive(node.right);
    }

    void preorderTraversal() {
        System.out.print("Pre-order Traversal: ");
        preorderTraversalRecursive(root);
        System.out.println();
    }

    private void preorderTraversalRecursive(Node node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        preorderTraversalRecursive(node.left);
        preorderTraversalRecursive(node.right);
    }

    void postorderTraversal() {
        System.out.print("Post-order Traversal: ");
        postorderTraversalRecursive(root);
        System.out.println();
    }

    private void postorderTraversalRecursive(Node node) {
        if (node == null) return;
        postorderTraversalRecursive(node.left);
        postorderTraversalRecursive(node.right);
        System.out.print(node.value + " ");
    }

    void levelOrderTraversal() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        System.out.print("Level Order Traversal: ");
        java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.value + " ");

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        System.out.println();
    }

    // Clear tree
    void clear() {
        root = null;
        size = 0;
    }

    // Check if empty
    boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        BST bst = new BST();

        System.out.println("=== Testing Insert Operations ===");
        bst.insert(42);
        bst.insert(29);
        bst.insert(54);
        bst.insert(45);
        bst.insert(37);
        bst.insert(12); // Duplicate will be ignored
        bst.insert(76);
        bst.insert(11);
        bst.insert(13);

        System.out.println("BST after insertions:");
        bst.printArray();
        bst.printTree();
        bst.printTreeStructure();
        System.out.println("BST property satisfied: " + bst.isBSTPropertySatisfied());
        System.out.println("Height: " + bst.getHeight());
        System.out.println("Size: " + bst.size);

        /*

        // Test search
        System.out.println("\n=== Testing Search ===");
        System.out.println("Search 29: " + bst.search(29));
        System.out.println("Search 100: " + bst.search(100));

        // Test findMin/Max
        System.out.println("\n=== Testing FindMin/Max ===");
        System.out.println("Minimum: " + bst.findMin());
        System.out.println("Maximum: " + bst.findMax());

        // Test traversals
        System.out.println("\n=== Testing Traversals ===");
        bst.inorderTraversal();  // Should be sorted
        bst.preorderTraversal();
        bst.postorderTraversal();
        bst.levelOrderTraversal();
        */

        // Test delete
        System.out.println("\n=== Testing Delete ===");
        bst.delete(29);
        System.out.println("After deleting 14:");
        bst.printArray();
        bst.printTree();
        System.out.println("Size: " + bst.size);
        //         Test change value
        System.out.println("\n=== Testing Change Value ===");
        bst.UpdateValue(7, 50);
        System.out.println("After changing 7 to 50:");
        bst.printArray();
        bst.printTree();
        System.out.println("BST property satisfied: " + bst.isBSTPropertySatisfied());
        /*
        // Test findIndex
        System.out.println("\n=== Testing Find Index ===");
        System.out.println("Index of 29: " + bst.findIndex(29));
        System.out.println("Index of 50: " + bst.findIndex(50));

        // Test parent
        System.out.println("\n=== Testing Parent ===");
        System.out.println("Parent of 29: " + bst.getParentValue(29));
        System.out.println("Parent of 50: " + bst.getParentValue(50));

        // Test leaf detection
        System.out.println("\n=== Testing Leaf Detection ===");
        System.out.println("Is 29 leaf? " + bst.isLeaf(29));
        System.out.println("Is 13 leaf? " + bst.isLeaf(13));

        // Test completeness (BST may not be complete)
        System.out.println("\n=== Testing Completeness ===");
        System.out.println("Is tree complete? " + bst.isComplete());

        // Test clear
        System.out.println("\n=== Testing Clear ===");
        bst.clear();
        System.out.println("After clear, is empty? " + bst.isEmpty());

        */
    }
}