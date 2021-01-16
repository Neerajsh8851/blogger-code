import java.util.Scanner;
      
        import java.util.Stack;

        public class Calculator {
          public static void main(final String[] args) {
            final Scanner scanner = new Scanner(System.in);
            String userInput;
  
            // main loop  exit command : /exit
            while(true) {
              userInput = scanner.nextLine().trim();
              if(userInput.equals("/exit")) {
                break;
      }
              else {
                userInput = infixToPostfix(userInput);
                System.out.println(result(userInput));
           
      }

      
    }

            scanner.close();    
 }
         static String infixToPostfix(String infixExp) {
           final Stack<Character> stack = new Stack<>();
           String postfix = "";
     
           //Remove all spaces 
           infixExp = infixExp.replaceAll(" *", "");      

           // Make a char array from infixExp,
           for (final char ch : infixExp.toCharArray()) {
             if(Character.isDigit(ch)) 
             postfix += ch;
             else if (stack.isEmpty()) {
               stack.push(ch);
               postfix += " ";
     }
             else if(ch == '(')
     
             stack.push(ch);
    
             else if(ch == ')') {
               while(!stack.isEmpty() && stack.peek() != '(') {
                 postfix += " " + stack.pop();
       }
               stack.pop();
     }
             else if (operatorPrecedence(ch) > operatorPrecedence(stack.peek())) {         
               stack.push(ch);
               postfix += " ";
     }
             else postfix += " " + stack.pop();
        
   }
           while (!stack.isEmpty()) {
             postfix += " " + stack.pop();  
   }
           return postfix;
 }

         static short operatorPrecedence(final char ch) {         
            switch (ch) {
             case '+':
             case '-': return 1;
             case '*':
             case '/': return 2;
             case '^': return 3;
             default:
               return 0;  
   }
      
 }

         static Double result(final String postfixExp) {
           final String[] items = postfixExp.split(" "); 
           final Stack<Double> stack  = new Stack<>();
 
           for(final String item : items) {
             if (item.matches("\\d+"))
             stack.push(Double.valueOf(item));
             else {
               final Double y = stack.pop();
               final Double x = stack.pop();
               stack.push(arithmeticOP(x, y, item));
     }  
   }
           return stack.pop();
 }

      

      

         static Double arithmeticOP(final Double x, final Double y, final String op) {

           switch (op) {

             case "+": return x + y;
             case "-": return x - y;
             case "*": return x * y;
             case "/": return x / y;
             case "^": return Math.pow(x, y);
             default: return 0.0;    

      
   }
 
 }     
}
