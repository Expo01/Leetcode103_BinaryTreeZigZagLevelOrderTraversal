import javax.swing.tree.TreeNode;
import java.util.*;


class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {return new ArrayList<List<Integer>>();}

        List<List<Integer>> results = new ArrayList<List<Integer>>();

        LinkedList<TreeNode> node_queue = new LinkedList<TreeNode>(); // add the root & delimiter to start the BFS loop
        node_queue.addLast(root);
        node_queue.addLast(null);

        LinkedList<Integer> level_list = new LinkedList<Integer>();
        boolean is_order_left = true;

        while (node_queue.size() > 0) {
            TreeNode curr_node = node_queue.pollFirst();
            if (curr_node != null) {
                if (is_order_left)
                    level_list.addLast(curr_node.val); // adds parent node to current level like a queue
                else
                    level_list.addFirst(curr_node.val); // adds parent node to current level like a stack horizontal stack with top(head) on L
                // such that if we have a queue: 1,2,3,4,5 where 1 is the head, it will dequeue and push onto level_list
                // like a stack level_list: 5,4,3,2,1 where recently popped stack node appends to head (left) of level_list
                // regardless of how it is added to level_list (effectivley in order or reverse) the children will still
                // be added to the node que in the order that parents came out of node que
                if (curr_node.left != null)
                    node_queue.addLast(curr_node.left); // adds to child que like queue
                if (curr_node.right != null)
                    node_queue.addLast(curr_node.right);

            } else {
                // we finish the scan of one level
                results.add(level_list); // add parents from current level
                level_list = new LinkedList<Integer>(); // create a new list for next set of parents from child que
                // prepare for the next level
                if (node_queue.size() > 0)
                    node_queue.addLast(null); // delimiter to distinguish row, set to null to break from the if statement on line 20
                is_order_left = !is_order_left; // change flag . seems like quick way to change a flags value
            }
        }
        return results;
    }
}


// MY WORKING SOLUTION, BEATS 90%
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root == null){ return new ArrayList<>();}
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        boolean flag = false;
        int rowSize = 0;
        int rowNumber = 0;
        ArrayDeque<TreeNode> q = new ArrayDeque<>();
        Stack<TreeNode> st = new Stack<>();
        q.add(root);

        while(!q.isEmpty()){
            rowSize = q.size();

            for(int i = 0; i < rowSize; i++){
                TreeNode node = q.pop();

                if(node.left != null){q.add(node.left);}
                if(node.right != null){q.add(node.right);}
                if (flag){ // if flag is true, add to stack for reversal after all children from row added
                    st.push(node);
                } else{ // if flag is false, then no stack needed to reverse input into the ans List.
                    ans.get(rowNumber).add(node.val);
                }
            }

            if(flag){
                while(!st.isEmpty()){
                    ans.get(rowNumber).add(st.pop().val);
                }
            }

            flag = !flag; // This is just a quick way to change flag value. elongated line-out below

//            if (!flag){
//                flag = true;
//            } else {
//                flag = false;
//            }
            rowNumber++;
            if(!q.isEmpty()){
                ans.add(new ArrayList<>());
            }
        }
        return ans;
    }
}

// idea: boolean flag to switch directtions. will need size variable to assess each row size in outer while loop
// then two inner while loop variations where L -> R will execute if flag == true, but if flag false then decrementing
// for loop from 0-stored size var?
// think the decremenning won't work but i will need a second loop condition where i empty the que items 0-size into
// a stack




/*

                    3
                  /   \
                9     20
              /  \   /  \
             6    8  15  7


- initiate que with 3
- flag is false so second loop add 20 and 9 to que. but will need to start with 9 for L->R next row
- flag = true, deque 20 and push to stack, deque 9 and push to stack then pop 9 and 20 adding children L to R and
reset flag to false?

 */
