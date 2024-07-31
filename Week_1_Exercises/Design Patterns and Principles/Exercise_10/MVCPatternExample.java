package MVC_Pattern_Example;

public class MVCPatternExample {
    public static void main(String[] args) {
        // Create a model (Student)
        Student student = new Student("John Doe", 1, "A");

        // Create a view (StudentView)
        StudentView view = new StudentView();

        // Create a controller (StudentController)
        StudentController controller = new StudentController(student, view);

        // Update the view to display the initial details
        controller.updateView();

        // Update the model data
        controller.setStudentName("Jane Doe");
        controller.setStudentGrade("B");

        // Update the view to display the updated details
        controller.updateView();
    }

    // Define Model Class
    public static class Student {
        private String name;
        private int id;
        private String grade;

        // Constructor
        public Student(String name, int id, String grade) {
            this.name = name;
            this.id = id;
            this.grade = grade;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }

    // Define View Class
    public static class StudentView {
        public void displayStudentDetails(String studentName, int studentId, String studentGrade) {
            System.out.println("Student Details:");
            System.out.println("Name: " + studentName);
            System.out.println("ID: " + studentId);
            System.out.println("Grade: " + studentGrade);
        }
    }

    // Define Controller Class
    public static class StudentController {
        private Student model;
        private StudentView view;

        public StudentController(Student model, StudentView view) {
            this.model = model;
            this.view = view;
        }

        public void setStudentName(String name) {
            model.setName(name);
        }

        public String getStudentName() {
            return model.getName();
        }

        public void setStudentId(int id) {
            model.setId(id);
        }

        public int getStudentId() {
            return model.getId();
        }

        public void setStudentGrade(String grade) {
            model.setGrade(grade);
        }

        public String getStudentGrade() {
            return model.getGrade();
        }

        public void updateView() {
            view.displayStudentDetails(model.getName(), model.getId(), model.getGrade());
        }
    }
}

