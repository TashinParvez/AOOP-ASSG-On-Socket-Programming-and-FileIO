import java.util.Scanner;

public class MeshNetwork {
    public static void main(String[] args) {
        String[] options = {"Server", "Client"};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Run as: 1) Server 2) Client");
        int choice = scanner.nextInt();

        if (choice == 1) {
            Server.main(args);
        } else if (choice == 2) {
            Client.main(args);
        } else {
            System.out.println("Invalid choice");
        }
    }
}