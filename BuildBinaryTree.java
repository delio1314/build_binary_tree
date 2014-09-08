import java.util.*;

public class BuildBinaryTree {

    /* 输入某二叉树的前序遍历和中序遍历结果,重建该二叉树.
     * 假设输入的前序遍历和中序遍历不含有重复的数字
     * 如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
     */

    public static void main(String[] args) {
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] ino = {4,7,2,1,5,3,8,6};
        Node tree = buildTree(pre, 0, ino, ino.length - 1, ino.length);
        levelPrint(tree);
        prePrint(tree);
        inPrint(tree);
        postPrint(tree);
    }

    public static Node buildTree(int[] pre, int start, int[] ino, int end, int length) {
        //参数验证
        if (pre == null || pre.length == 0 || ino == null
            || ino.length == 0 || length <= 0) {
            return null;
        }
        //建立子树根节点
        int val = pre[start];
        Node root = new Node(val);
        //递归终止条件：子树只有一个节点
        if (length == 1)
            return root;
        //分拆子树的左子树和右子树
        int i = 0;
        while (i < length) {
            if (val == ino[end - i]) {
                break;
            }
            i++;
        }
        //建立子树的左子树
        root.left = buildTree(pre, start + 1, ino, end - i - 1, length - 1 - i);
        //建立子树的右子树
        root.right = buildTree(pre, start + length - i, ino, end, i );

        return root;
    }

    public static void levelPrint(Node node) {
        // 层序遍历打印二叉树
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node node_temp = queue.poll();
            System.out.print(node_temp.val + " ");
            if (node_temp.left != null) {
                queue.add(node_temp.left);
            }
            if (node_temp.right != null) {
                queue.add(node_temp.right);
            }
        }
        System.out.println();
    }

    public static void prePrint(Node node) {
        // 前序遍历打印二叉树
        LinkedList<Node> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()) {
            while(node != null) {
                System.out.print(node.val + " ");
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            node = node.right;
        }
        System.out.println();
    }

    public static void inPrint(Node node) {
        // 中序遍历打印二叉树
        LinkedList<Node> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()) {
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            System.out.print(node.val + " ");
            node = node.right;
        }
        System.out.println();
    }

    public static void postPrint(Node node) {
        // 用一个数组标记右结点是否被访问过
        // boolean变量的默认初始值位false
//        int depth = depth(node);
        boolean[] flags = new boolean[100];
        // 后序遍历打印二叉树
        LinkedList<Node> stack = new LinkedList<>();
        while (node != null) {
            stack.push(node);
            // true表示没有被访问过
            flags[stack.size()] = true;
            node = node.left;
        }
        while (!stack.isEmpty()) {
            node = stack.peek();
            if (node.right != null && flags[stack.size()]) {
                node = node.right;
                flags[stack.size()] = false;
                while (node != null) {
                    stack.push(node);
                    flags[stack.size()] = true;
                    node = node.left;
                }
                node = stack.peek();
            }
            node = stack.pop();
            System.out.print(node.val + " ");
        }
        System.out.println();
    }

    public static int depth(Node node) {
        if(node == null) {
            return 0;
        } else {
            return 1 + Math.max(depth(node.left), depth(node.right));
        }
    }
}

class Node {
    int val;
    Node left, right;
    Node(int val) {
        this.val = val;
    }
}