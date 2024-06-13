package lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class generateNewTextTest {

    private lab.DirectedGraph graph;

    @Before
    public void setUp() throws IOException {
        // 初始化DirectedGraph对象
        graph = new lab.DirectedGraph();

        // 读取测试文件并构建有向图
        String filePath = "textfile.txt";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line);
                textBuilder.append(" "); // 假设每行后需要空格
            }
            String text = textBuilder.toString();

            // 调用构建有向图的方法
            graph.buildGraphFromText(text);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test file or build graph", e);
        }
    }

    private void runTestCase(String inputText, String expectedOutput) {
        String actualOutput = graph.generateNewText(inputText);
        System.out.println("Input: " + inputText);
        System.out.println("Expected Output: " + expectedOutput);
        System.out.println("Actual Output: " + actualOutput);
        Assert.assertTrue("Test failed: expected " + expectedOutput + " but got " + actualOutput,
                expectedOutput.equals(actualOutput));
        System.out.println("Test " + (actualOutput.equals(expectedOutput) ? "PASSED" : "FAILED"));
        System.out.println();
    }

    // 测试用例方法
    @Test
    public void testGenerateNewTextWithEmptyInput() {
        runTestCase("", "");
    }

    @Test
    public void testGenerateNewTextWithInvalidInput() { runTestCase("+-*/", "+-*/"); }

    @Test
    public void testGenerateNewTextWithSingleWord() {
        runTestCase("hello", "hello");
    }

    @Test
    public void testGenerateNewTextWithWordsButNoBridgeWords() {
        runTestCase("hello my", "hello my");
    }

    @Test
    public void testGenerateNewTextWithWordsAndOneBridgeWord() {
        runTestCase("have a day", "have a good day");
    }

    @Test
    public void testGenerateNewTextWithWordsAndBridgeWords() {
        String input = "hello world";
        String generatedText = graph.generateNewText(input); // 假设这个方法存在并返回生成的文本

        String expectedPattern = "hello (my|beautiful)\\s+world\\b";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(expectedPattern);
        java.util.regex.Matcher matcher = pattern.matcher(generatedText);

        System.out.println("Input: " + input);
        System.out.println("Expected Output: 'hello my/beautiful world'");
        System.out.println("Actual Output: " + generatedText);

        Assert.assertTrue("Test failed: expected to match pattern \"" + expectedPattern + "\" but got \"" + generatedText + "\"", matcher.matches());
        System.out.println("Test " + (matcher.matches() ? "PASSED" : "FAILED"));
    }

}
