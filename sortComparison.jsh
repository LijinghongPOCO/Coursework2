ArrayList<Integer> cardCompare(String card1, String card2) {
    // 创建花色优先级的映射（在函数内部）
    HashMap<Character, Integer> suitPriority = new HashMap<>();
    suitPriority.put('H', 1); // Hearts
    suitPriority.put('C', 2); // Clubs
    suitPriority.put('D', 3); // Diamonds
    suitPriority.put('S', 4); // Spades

    ArrayList<Integer> result = new ArrayList<>();

    // 提取两张牌的数字和花色
    char suit1 = card1.charAt(card1.length() - 1);
    char suit2 = card2.charAt(card2.length() - 1);

    int value1 = Integer.parseInt(card1.substring(0, card1.length() - 1)); // 获取牌面数字
    int value2 = Integer.parseInt(card2.substring(0, card2.length() - 1)); // 获取牌面数字

    // 比较花色优先级
    if (suitPriority.get(suit1) < suitPriority.get(suit2)) {
        result.add(-1);  // card1 < card2
    } else if (suitPriority.get(suit1) > suitPriority.get(suit2)) {
        result.add(1);   // card1 > card2
    } else {
        // 如果花色相同，比较牌面数字
        if (value1 < value2) {
            result.add(-1);  // card1 < card2
        } else if (value1 > value2) {
            result.add(1);   // card1 > card2
        } else {
            result.add(0);   // card1 == card2
        }
    }

    return result;
}

ArrayList<Integer> bubbleSort(ArrayList<String> array) {
    int n = array.size();
    ArrayList<Integer> result = new ArrayList<>();

    // 初始化索引列表
    for (int i = 0; i < n; i++) {
        result.add(i);  // 初始时结果是原始的索引顺序
    }

    // 冒泡排序算法
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - 1 - i; j++) {
            // 使用 cardCompare 函数比较相邻元素
            if (cardCompare(array.get(result.get(j)), array.get(result.get(j + 1))).get(0) > 0) {
                // 如果相邻扑克牌顺序不正确，交换它们的索引
                int temp = result.get(j);
                result.set(j, result.get(j + 1));
                result.set(j + 1, temp);
            }
        }
    }

    // 根据排序后的索引列表获取扑克牌，并打印排序后的扑克牌列表
    ArrayList<String> sortedCards = new ArrayList<>();
    for (int index : result) {
        sortedCards.add(array.get(index));  // 根据排序后的索引顺序获取扑克牌
    }

    // 打印排序后的扑克牌列表
    System.out.println(sortedCards);  // 打印排序后的扑克牌列表

    return result; 
}

ArrayList<Integer> mergeSort(ArrayList<String> array) {
    int n = array.size();
    ArrayList<Integer> result = new ArrayList<>();

    // 初始化索引列表
    for (int i = 0; i < n; i++) {
        result.add(i);  // result 中存储的是原始扑克牌的索引
    }

    // 执行归并排序，排序的是索引列表
    result = mergeSortHelper(array, result);

    // 根据排序后的索引，打印排序后的扑克牌列表
    ArrayList<String> sortedCards = new ArrayList<>();
    for (int index : result) {
        sortedCards.add(array.get(index));  // 根据索引获取扑克牌
    }

    System.out.println(sortedCards);  // 打印排序后的扑克牌列表

    return result;  // 返回排序后的索引列表
}

// 归并排序的辅助函数
private ArrayList<Integer> mergeSortHelper(ArrayList<String> array, ArrayList<Integer> indices) {
    int n = indices.size();
    
    if (n <= 1) {
        return indices;
    }

    // 分割索引列表
    int middle = n / 2;
    ArrayList<Integer> left = new ArrayList<>(indices.subList(0, middle));
    ArrayList<Integer> right = new ArrayList<>(indices.subList(middle, n));

    // 递归排序左右部分
    left = mergeSortHelper(array, left);
    right = mergeSortHelper(array, right);

    // 合并两个已排序的索引列表
    return merge(array, left, right);
}

// 合并两个已排序的索引列表
private ArrayList<Integer> merge(ArrayList<String> array, ArrayList<Integer> left, ArrayList<Integer> right) {
    ArrayList<Integer> result = new ArrayList<>();
    int i = 0, j = 0;

    // 合并过程，按照牌的大小顺序合并索引
    while (i < left.size() && j < right.size()) {
        if (cardCompare(array.get(left.get(i)), array.get(right.get(j))).get(0) <= 0) {
            result.add(left.get(i));
            i++;
        } else {
            result.add(right.get(j));
            j++;
        }
    }

    // 将剩余的元素添加到结果中
    result.addAll(left.subList(i, left.size()));
    result.addAll(right.subList(j, right.size()));

    return result;
}

long measureBubbleSort(String filename) throws IOException {
    // 读取文件内容到 ArrayList
    ArrayList<String> cards = new ArrayList<>();
    
    // 使用 BufferedReader 读取文件
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            cards.add(line.trim());  // 每行添加到 cards 列表中，去除多余的空格
        }
    }

    // 记录开始时间
    long startTime = System.currentTimeMillis();

    // 执行冒泡排序
    bubbleSort(cards);

    // 记录结束时间
    long endTime = System.currentTimeMillis();

    // 返回排序所花费的时间（毫秒）
    return endTime - startTime;
}

long measureMergeSort(String filename) throws IOException {
    // 读取文件内容到 ArrayList
    ArrayList<String> cards = new ArrayList<>();
    
    // 使用 BufferedReader 读取文件
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            cards.add(line.trim());  // 每行添加到 cards 列表中，去除多余的空格
        }
    }

    // 记录开始时间
    long startTime = System.currentTimeMillis();

    // 执行归并排序
    mergeSort(cards);

    // 记录结束时间
    long endTime = System.currentTimeMillis();

    // 返回排序所花费的时间（毫秒）
    return endTime - startTime;
}

void sortComparison(String[] filenames) {    
    StringBuilder results = new StringBuilder(",10,100,10000\n");  
     
    long[] bubbleTimes = new long[filenames.length];  
    long[] mergeTimes = new long[filenames.length];  
 
    for (int i = 0; i < filenames.length; i++) {  
        try {  
            bubbleTimes[i] = measureBubbleSort(filenames[i]);  
            mergeTimes[i] = measureMergeSort(filenames[i]);  
        } catch (Exception e) {  
            System.out.println("Error processing " + filenames[i] + ": " + e.getMessage());  
        }  
    }  
  
    results.append("bubbleSort,");  
    for (long time : bubbleTimes) {  
        results.append(time).append(",");  
    }  
    results.setLength(results.length() - 1);   
    results.append("\n");   
  
    results.append("mergeSort,");  
    for (long time : mergeTimes) {  
        results.append(time).append(",");   
    }  
    results.setLength(results.length() - 1);   
    results.append("\n");   

    try {    
        Files.write(Paths.get("sortComparison.csv"), results.toString().getBytes(), StandardOpenOption.CREATE);  
    } catch (Exception e) {  
        System.out.println("Error writing to file: " + e.getMessage());  
    }  
}