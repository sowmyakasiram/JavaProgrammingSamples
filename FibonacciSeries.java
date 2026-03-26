public class FibonacciSeries {
    
    // Fibonacci method using iterative approach
    public static long fibonacci(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        
        long prev = 0, curr = 1;
        for (int i = 2; i <= n; i++) {
            long next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }
    
    public static void main(String[] args) {
        // Test Case 1: fibonacci(0) = 0
        testFibonacci(0, 0);
        
        // Test Case 2: fibonacci(1) = 1
        testFibonacci(1, 1);
        
        // Test Case 3: fibonacci(5) = 5
        testFibonacci(5, 5);
        
        // Test Case 4: fibonacci(10) = 55
        testFibonacci(10, 55);
        
        // Test Case 5: fibonacci(15) = 610
        testFibonacci(15, 610);
        
        // Test Case 6: fibonacci(20) = 6765
        testFibonacci(20, 6765);
        
        // Test Case 7: fibonacci(8) = 21
        testFibonacci(8, 21);
        
        // Test Case 8: fibonacci(12) = 144
        testFibonacci(12, 144);
        
        // Test Case 9: fibonacci(7) = 13
        testFibonacci(7, 13);
        
        // Test Case 10: fibonacci(6) = 8
        testFibonacci(6, 8);
        
        System.out.println("\n=== All 10 test cases executed ===");
    }
    
    // Helper method to test Fibonacci
    public static void testFibonacci(int n, long expected) {
        long result = fibonacci(n);
        String status = result == expected ? "PASS" : "FAIL";
        System.out.println("Test: fibonacci(" + n + ") = " + result + " | Expected: " + expected + " | Status: " + status);
    }
}