import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * 문자열 저장 및 탐색할 때, 시간 오버를 경험할 수 있다. 아래 트라이(Trie)구조는 그냥 외우자.
 * 
 * 트라이(Trie)는 문자열을 저장하고 효율적으로 탐색하기 위한 트리 형태의 자료구조이다.
 * 우리가 검색할 때 볼 수 있는 자동완성 기능, 사전 검색 등 문자열을 탐색하는데 특화되어있는 자료구조라고 한다.
 * 래딕스 트리(radix tree) or 접두사 트리(prefix tree) or 탐색 트리(retrieval tree)라고도 한다. 트라이는 retrieval tree에서 나온 단어이다.
 * 예를 들어 'Datastructure'라는 단어를 검색하기 위해서는 제일 먼저 'D'를 찾고, 다음에 'a', 't', ... 의 순서로 찾으면 된다. 이러한 개념을 적용한 것이 트라이(Trie)이다.
 * 
 * 트라이(Trie) 장단점
 * - 트라이(Trie)는 문자열 검색을 빠르게 한다.
 * - 문자열을 탐색할 때, 하나하나씩 전부 비교하면서 탐색을 하는 것보다 시간 복잡도 측면에서 훨씬 더 효율적이다.
 * - 각 노드에서 자식들에 대한 포인터들을 배열로 모두 저장하고 있다는 점에서 저장 공간의 크기가 크다는 단점도 있다. (메모리 측면에서 비효율적일 수 있음!)
 * 
 * [예제] words = ['aab', 'defgab', 'abcde', 'aabcde', 'bbbbbbbbbb', 'jabjjjad']
<<Step-by-Step Execution>>
1. Word: "aab"
- Insert "a": Root 노드에서 자식 노드에 'a'가 없으므로 새로운 TrieNode 추가.
- Insert "a": 이전 노드의 자식 노드에 'a'가 없으므로 새로운 TrieNode 추가.
- Insert "b": 이전 노드의 자식 노드에 'b'가 없으므로 새로운 TrieNode 추가.
- Mark end of word: 마지막 노드에 isEndOfWord를 true로 설정.
root
└─ a
   └─ a
      └─ b (isEndOfWord=true)

2. Word: "defgab"
- Insert "d": Root 노드에서 자식 노드에 'd'가 없으므로 새로운 TrieNode 추가.
- Insert "e": 이전 노드의 자식 노드에 'e'가 없으므로 새로운 TrieNode 추가.
- Insert "f": 이전 노드의 자식 노드에 'f'가 없으므로 새로운 TrieNode 추가.
- Insert "g": 이전 노드의 자식 노드에 'g'가 없으므로 새로운 TrieNode 추가.
- Insert "a": 이전 노드의 자식 노드에 'a'가 없으므로 새로운 TrieNode 추가.
- Insert "b": 이전 노드의 자식 노드에 'b'가 없으므로 새로운 TrieNode 추가.
- Mark end of word: 마지막 노드에 isEndOfWord를 true로 설정.
root
├─ a
│  └─ a
│     └─ b (isEndOfWord=true)
└─ d
   └─ e
      └─ f
         └─ g
            └─ a
               └─ b (isEndOfWord=true)

3. Word: "abcde"
- Insert "a": Root 노드에서 자식 노드에 'a'가 이미 존재함.
- Insert "b": 이전 노드의 자식 노드에 'b'가 없으므로 새로운 TrieNode 추가.
- Insert "c": 이전 노드의 자식 노드에 'c'가 없으므로 새로운 TrieNode 추가.
- Insert "d": 이전 노드의 자식 노드에 'd'가 없으므로 새로운 TrieNode 추가.
- Insert "e": 이전 노드의 자식 노드에 'e'가 없으므로 새로운 TrieNode 추가.
- Mark end of word: 마지막 노드에 isEndOfWord를 true로 설정.
root
├─ a
│  ├─ a
│  │  └─ b (isEndOfWord=true)
│  └─ b
│     └─ c
│        └─ d
│           └─ e (isEndOfWord=true)
└─ d
   └─ e
      └─ f
         └─ g
            └─ a
               └─ b (isEndOfWord=true)

4. Word: "aabcde"
- Insert "a": Root 노드에서 자식 노드에 'a'가 이미 존재함.
- Insert "a": 이전 노드의 자식 노드에 'a'가 이미 존재함.
- Insert "b": 이전 노드의 자식 노드에 'b'가 이미 존재함. (이 단어는 "aab"의 접두사이므로 중단) isEndOfWord가 true인 노드를 만났으므로 "BAD SET"을 출력하고 "aabcde"를 출력함.
[출력]
BAD SET
aabcde
 */

class TrieNode {
    boolean isEndOfWord = false;
    Map<Character, TrieNode> children = new HashMap<>();
}

class Result {

    /*
     * Complete the 'noPrefix' function below.
     *
     * The function accepts STRING_ARRAY words as parameter.
     */
     
    static TrieNode root = new TrieNode();
    static boolean insert(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
            
            if (current.isEndOfWord) {
                return false;
            }
        }
        
        // 예를 들어, ["abcd", "abc"]인 경우, 2번재 인수인 "abc"는 첫번째 문자열(abcd)의 prefix다.
        // 위 for 루프를 모두 체크하고 여기로 왔다는 이야기는, 트라이(Trie)구조에 "abc"를 insert를 모두 완료했으나,
        // 이 시점에서 'c' 노드의 자식 노드를 확인하면 'd' 노드가 있음.(abcd)
        // 이로 인해 "abc"는 삽입되지 않으며 "abc"가 "abcd"의 접두사이므로 "BAD SET"이 출력됨.
        if (!current.children.isEmpty()) {
            return false;
        }
        
        // 신규 단어인 경우, 아래와 같이 설정 후 종료
        current.isEndOfWord = true;
        return true;
    }

    public static void noPrefix(List<String> words) {
        // Write your code here
        for(String word : words) {
            if(!insert(word)) {
                System.out.println("BAD SET");
                System.out.println(word);
                return;
            }
        }
        System.out.println("GOOD SET");
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> words = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        Result.noPrefix(words);

        bufferedReader.close();
    }
}
