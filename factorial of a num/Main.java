        import java.util.Scanner;

        class Main {

            private static int fact(int n) {
                if (!(n > 1) || n == 0)
                    return 1;
                else
                    return n * fact(n - 1);
            }

            public static void main(String[] args) {
                Scanner scan = new Scanner(System.in);
                System.out.print("Enter the Value of N: ");
                int n = scan.nextInt();
                System.out.print("\nFactorial of N: " + fact(n));
                scan.close();
            }
        }
        
