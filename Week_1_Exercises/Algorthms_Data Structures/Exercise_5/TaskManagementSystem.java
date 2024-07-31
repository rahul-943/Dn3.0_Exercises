package Task_Management_System;

import java.util.Scanner;

public class TaskManagementSystem {

    // Task class
    public static class Task {
        private String taskId;
        private String taskName;
        private String status;

        public Task(String taskId, String taskName, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.status = status;
        }

        public String getTaskId() { return taskId; }
        public String getTaskName() { return taskName; }
        public String getStatus() { return status; }

        @Override
        public String toString() {
            return "Task{" +
                    "taskId='" + taskId + '\'' +
                    ", taskName='" + taskName + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    // Node class for the linked list
    private static class Node {
        Task task;
        Node next;

        Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    // Singly Linked List class
    private static class TaskLinkedList {
        private Node head;

        public TaskLinkedList() {
            head = null;
        }

        // Add a task to the end of the list
        public void addTask(Task task) {
            Node newNode = new Node(task);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        // Search for a task by ID
        public Task searchTaskById(String taskId) {
            Node current = head;
            while (current != null) {
                if (current.task.getTaskId().equals(taskId)) {
                    return current.task;
                }
                current = current.next;
            }
            return null;
        }

        // Traverse and print all tasks
        public void traverseTasks() {
            Node current = head;
            while (current != null) {
                System.out.println(current.task);
                current = current.next;
            }
        }

        // Delete a task by ID
        public boolean deleteTaskById(String taskId) {
            if (head == null) {
                return false;
            }
            if (head.task.getTaskId().equals(taskId)) {
                head = head.next;
                return true;
            }
            Node current = head;
            while (current.next != null) {
                if (current.next.task.getTaskId().equals(taskId)) {
                    current.next = current.next.next;
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }

    // Main method to handle user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskLinkedList taskList = new TaskLinkedList();

        while (true) {
            System.out.println("Task Management System");
            System.out.println("1. Add Task");
            System.out.println("2. Search Task by ID");
            System.out.println("3. Traverse Tasks");
            System.out.println("4. Delete Task by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task ID: ");
                    String taskId = scanner.nextLine();
                    System.out.print("Enter task name: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Enter status: ");
                    String status = scanner.nextLine();
                    taskList.addTask(new Task(taskId, taskName, status));
                    break;

                case 2:
                    System.out.print("Enter task ID to search: ");
                    taskId = scanner.nextLine();
                    Task task = taskList.searchTaskById(taskId);
                    if (task != null) {
                        System.out.println("Task found: " + task);
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 3:
                    System.out.println("Tasks:");
                    taskList.traverseTasks();
                    break;

                case 4:
                    System.out.print("Enter task ID to delete: ");
                    taskId = scanner.nextLine();
                    if (taskList.deleteTaskById(taskId)) {
                        System.out.println("Task deleted.");
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
