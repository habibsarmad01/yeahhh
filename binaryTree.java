package Trees;
class Node{
    int data;
    Node left, right;

    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class binary_Tree{
    Node root;
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;

    binary_Tree(Node root){
        this.root = root;
    }

    void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    int size(Node root){
        //Another way to get size of tree using recursion
        if (root == null){
            return 0;
        }
        return 1 +  size(root.left) + size(root.right);
    }

    int sum(Node root){
        if (root == null ){
            return 0;
        }
        return root.data + sum(root.left) + sum(root.right);
    }
    int prod(Node root){
        //Product of Non zero Nodes - if we want product of all nodes just remove 2nd if condition
        if (root == null){
            return 1;
        }
        if(root.data == 0){
            return prod(root.left) * prod(root.right); // Notice we don't multiply by root.data when root.data is zero
        }
        return root.data * prod(root.left) * prod(root.right);
    }

    int findMax(Node root){
      if (root == null){
          return max;
      }
      if(root.data > max) max = root.data;
      if(root.data < min) min = root.data;

      findMax(root.left);
      findMax(root.right);

      return max;
   }

   int level(Node root){
        if (root == null){
            return 0;
        }
        return 1 + Math.max(level(root.left), level(root.right));
   }
}
public class binaryTree {
    public static void main(String[] args) {
        Node root = new Node(100);
        root.left = new Node(0);
        root.right = new Node(3);
        root.left.left = new Node(14);
        root.left.right = new Node(5);
//        root.right.left = new Node(6);
//        root.right.right = new Node(0);

        binary_Tree tree = new binary_Tree(root);
        tree.preOrder(root);
//        System.out.println("Size: " + tree.getSize());
        System.out.println();
        System.out.println("Size: " + tree.size(root));
        System.out.println("Sum: " + tree.sum(root));
        System.out.println("Product: " + tree.prod(root));
        System.out.println("MaxNode: " + tree.findMax(root));
        System.out.println("Levels: " + tree.level(root));
    }
}
