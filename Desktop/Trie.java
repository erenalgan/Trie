class TrieNode {
    int count;
    SortedMap<Character, TrieNode> childNodes;

    public TrieNode(int count){
        this.count = count;
        this.childNodes = new TreeMap<Character, TrieNode>();
    }
}

class Trie{
    TrieNode root;

    public Trie(){
        this.root = new TrieNode(0);
    }

    public void insertTrie(String word) {
        SortedMap<Character, TrieNode> nextLetters = this.root.childNodes;

        // all but the last letter in the word
        for ( int i = 0, n = word.length() - 1; i < n; ++i ) {
            char letter = word.charAt(i);

            if (!nextLetters.containsKey(letter)) {
                nextLetters.put(letter, new TrieNode(0));
            }

            nextLetters = nextLetters.get(letter).childNodes;
        }

        // the last letter, so set or update count appropriately
        char letter = word.charAt(word.length() - 1);
        TrieNode node = nextLetters.get(letter);
        if ( null == node ) {
            nextLetters.put(letter, new TrieNode(1));
        } else {
            node.count++;
        }
    }

    public boolean searchTrie(String word) {
        SortedMap<Character, TrieNode> nextLetters;
        TrieNode node = this.root;

        for ( Character letter : word.toCharArray() ) {
            nextLetters = node.childNodes;
            node = nextLetters.get(letter);

            if ( node == null ) {
                return false;
            }
        }

        return node.count > 0;
    }

    public void removeTrie(String word) {
        if (word == null || word.length == 0)
            return;
        SortedMap<Character, TrieNode> nextLetters = this.root.childNodes;
        Stack<SortedMap<Character, TrieNode>> levels = new Stack<>();

        for (int i = 0; i < word.length - 1; i++) {
            char letter = word.charAt(i);
            levels.push(nextLetters);
            if (!nextLetters.containsKey(letter))
                return; 
            nextLetters = nextLetters.get(letter).childNodes;
        }

        char letter = word.charAt(word.length() - 1);
        TrieNode node = nextLetters.get(letter);
        
        if (node == null || node.count == 0)
            return;

        //Prune branches if necessary.
        node.count--;
        if (node.count == 0 && node.childNodes.size() == 0) {
            nextLetters.remove(letter);
            while (!levels.isEmpty()) {
                nextLetters = levels.pop();
                letter = word.charAt(levels.size());
                node = nextLetters.get(letter);
                if (node.childNodes.size() != 0)
                    break;

                nextLetters.remove(letter);
            }
        }
    }
}
