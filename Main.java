import java.util.*;

interface Analyzer {
    void analyze(User user);
}

class CareerGoal {
    private String role;

    public CareerGoal(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

abstract class Person {
    protected String name;
    protected String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void displayProfile();
}

class Skill {
    private String name;

    public Skill(String name) {
        this.name = name.toLowerCase();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Project {
    private String title;

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

class User extends Person {

    private List<Skill> skills = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private CareerGoal goal;

    public User(String name, String email) {
        super(name, email);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setGoal(CareerGoal goal) {
        this.goal = goal;
    }

    public CareerGoal getGoal() {
        return goal;
    }

    @Override
    public void displayProfile() {
        System.out.println("\n===== USER PROFILE =====");
        System.out.println("Name : " + name);
        System.out.println("Email: " + email);
        System.out.println("Skills: " + skills);
    }
}

class ReportGenerator {

    public static void generate(User user) {
        System.out.println("\n===== PROFILE REPORT =====");
        user.displayProfile();
        System.out.println("Career Goal: " + user.getGoal().getRole());
    }
}

class SkillGapAnalyzer implements Analyzer {

    private final Map<String, List<String>> roleSkills = new HashMap<>();

    public SkillGapAnalyzer() {

        roleSkills.put(
            "java developer",
            Arrays.asList(
                "java",
                "oops",
                "collections",
                "jdbc",
                "sql",
                "git",
                "spring boot"
            )
        );

        roleSkills.put(
            "backend engineer",
            Arrays.asList(
                "java",
                "spring boot",
                "rest api",
                "sql",
                "docker",
                "git"
            )
        );
    }

    @Override
    public void analyze(User user) {

        String role = user.getGoal().getRole().toLowerCase();

        List<String> required =
                roleSkills.getOrDefault(role, new ArrayList<>());

        List<String> missing = new ArrayList<>();

        for (String req : required) {

            boolean found = false;

            for (Skill s : user.getSkills()) {

                if (s.getName().equalsIgnoreCase(req)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                missing.add(req);
            }
        }

        System.out.println("\n===== SKILL GAP REPORT =====");
        System.out.println("Target Role : " + user.getGoal().getRole());
        System.out.println("Missing Skills: " + missing);

        if (!missing.isEmpty()) {

            System.out.println("\nLearning Roadmap:");

            int i = 1;

            for (String skill : missing) {
                System.out.println(i++ + ". Learn " + skill);
            }
        } else {
            System.out.println("Excellent! You already have all required skills.");
        }
    }
}

class ProgressAnalyzer implements Analyzer {

    @Override
    public void analyze(User user) {

        System.out.println("\n===== PROGRESS REPORT =====");
        System.out.println("Total Skills Added : " + user.getSkills().size());
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println(" Smart Career Tracker & Skill Analyzer ");
        System.out.println("======================================");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        User user = new User(name, email);

        System.out.print("Target Role (Java Developer/Backend Engineer): ");
        user.setGoal(new CareerGoal(sc.nextLine()));

        System.out.println("\nEnter your skills (type 'done' to finish):");

        while (true) {

            String skill = sc.nextLine();

            if (skill.equalsIgnoreCase("done")) {
                break;
            }

            user.addSkill(new Skill(skill));
        }

        ReportGenerator.generate(user);

        Analyzer gapAnalyzer = new SkillGapAnalyzer();
        gapAnalyzer.analyze(user);

        Analyzer progressAnalyzer = new ProgressAnalyzer();
        progressAnalyzer.analyze(user);

        sc.close();
    }
}