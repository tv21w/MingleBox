import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;



class Project {
    String projectName;
    String description;
    int id=1;
    static int c=1;
    int pricePrediction;
    public Project()
    {

    }
    public Project(int i, String projectName, String description, int pricePrediction) {
        this.projectName = projectName;
        this.description = description;
        this.id=i;
        this.pricePrediction = pricePrediction;
        c++;

    }
    public String toString()
    {
        return "id: "+ id +"\nName: "+projectName+"\ndescription: "+description+"\nPrice: "+pricePrediction;
    }
    public double getPricePrediction() {
        return pricePrediction;
    }

    // Other methods: Project details, etc.
}



class Admin {
    List<Project> projectsForCoder = new ArrayList<>();
    List<Project> projectsForBuyer = new ArrayList<>();

    public boolean checkAdmin(String username,String password)
    {
        if(username.compareToIgnoreCase("vt")==0 && password.compareTo("0987")==0)
        {
            return true;
        }else{
            return false;
        }
    }
    public void uploadProject(Scanner sc,char c) {
        String projectName;
        String description;
        int pricePrediction;
        System.out.println("Enter project name ,description, price :");
        projectName= sc.nextLine();
        description=sc.nextLine();

        pricePrediction=sc.nextInt();

        Project project = new Project(Project.c,projectName, description,pricePrediction);
        sc.nextLine();
        if(c=='c'||c=='C')
            projectsForCoder.add(project);
        else
            projectsForBuyer.add(project);
    }

    public void showProjectForBuyer()
    {
        if(projectsForBuyer.size()==0)
        {
            System.out.println("No project added");
            return ;
        }
        System.out.println("Projects available are \n  :");
        for (Project i:projectsForBuyer)
        {
            System.out.println(i);
        }
    }

    public void showProjectForCoder()
    {
        if(projectsForCoder.size()==0)
        {
            System.out.println("No project added");
            return ;
        }
        System.out.println("These are the project newly uploaded for code");

        for (Project i:projectsForCoder)
        {
            System.out.println(i);
        }
    }
    public void chooseProjectBybuyer(User u1,Scanner sc)
    {
        int m=0,price=0;

        Random r=new Random();
        int d=r.nextInt(projectsForCoder.size());
        System.out.println(d);
        for (Project i:projectsForCoder)
        {
            if(d==i.id)
            {
                price=i.pricePrediction;
                break;
            }
        }

        System.out.println("\nPrice of project: "+price+"\nWhat price do you suggest");
        price=sc.nextInt();
        approveProjectForBuyer(d,price);
    }
    public  void approveProjectForBuyer(int id,int givenPrice)
    {
        int actualPrice = 0;
        ApprovedProjects obj=new ApprovedProjects();
        Project temp=new Project();
        for (Project i:projectsForBuyer)
        {
            if(id==i.id)
            {
                actualPrice=i.pricePrediction;
                temp=i;
                break;
            }
        }
        if(actualPrice>=givenPrice)
        {
            obj.setProjectForBuyer(temp);
        }
    }

    public void chooseProjectByCoder(User u1,Scanner sc)
    {
        int m=0,price=0;
        Random r=new Random();
        for (Project i:projectsForCoder)
        {
            if(m< i.id)
            {
                price=i.pricePrediction;
                m=i.id;
            }
        }
        int d=r.nextInt(m);// id
        System.out.println(d);
        System.out.println("\nWhat price u want to take for the project current price is "+price+" Enter ur price ");
        price=sc.nextInt();
        approveProjectForCoder(d,price);
    }
    public void approveProjectForCoder(int id,int givenPrice)
    {
        int actualPrice = 0;
        ApprovedProjects obj=new ApprovedProjects();
        Project temp=new Project();
        for (Project i:projectsForCoder)
        {
            if(id==i.id)
            {
                actualPrice=i.pricePrediction;
                temp=i;
                break;
            }
        }
        if(actualPrice>givenPrice)
        {
            obj.setProjectForCoder(temp);
        }
    }

    public void approvedProject() {
        ApprovedProjects obj=new ApprovedProjects();
        ArrayList<Project>pfb=obj.getProjectForBuyer();
        ArrayList<Project>pfc=obj.getProjectForCoder();
        if(pfb.size()==0)
        {
            System.out.println("No project selled till now");
        }else
        {
            pfb.forEach((e)->System.out.println());
        }

        if(pfc.size()==0)
        {
            System.out.println("No project selled till now");
        }else
        {
            pfc.forEach((e)->System.out.println());
        }
    }
}

class ApprovedProjects extends Project {
    ArrayList<Project>projectForCoder=new ArrayList<>();
    ArrayList<Project>projectForBuyer=new ArrayList<>();
    public ArrayList<Project> getProjectForCoder() {
        return projectForCoder;
    }
    public void setProjectForCoder( Project  projectForCoder) {
        this.projectForCoder.add(projectForCoder) ;
    }
    public ArrayList<Project> getProjectForBuyer() {
        return projectForBuyer;
    }
    public void setProjectForBuyer( Project  projectForBuyer) {
        this.projectForBuyer.add(projectForBuyer);
    }


}

class User {
    String username;
    String password;

    public User() {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void  register(Scanner sc, ArrayList<User>a)
    {
        System.out.println("Enter username and password ");
        username=sc.nextLine();
        sc.nextLine();
        password=sc.nextLine();
        a.add(this);

    }
    public User login(ArrayList<User>coder,ArrayList<User>buyer,Scanner scanner,Admin admin)
    {
        char c;
        User loggedInUser=null;
        System.out.println("\n Enter 'a' for coder login  and 'b' for buyer login:");
        c=scanner.next().charAt(0);
        if(c=='a'||c=='b')
        {
            scanner.nextLine();
            System.out.println("Enter Username and password");
            String u= scanner.nextLine();

            String p=scanner.nextLine();
            for(User r:buyer)
            {
                if(u.compareToIgnoreCase(r.username)==0 &&(p.compareTo(r.password)==0))
                    loggedInUser= r;
                else
                    loggedInUser=null;
            }
            if (loggedInUser != null)
            {
                System.out.println("User logged in Succesfully,Welcome  "+loggedInUser.username);
                admin.showProjectForBuyer();

            }
            else
            {
                System.out.println("Invalid username or password");
            }
        }
        else
        {
            System.out.println("Enter Username and password");
            String u= scanner.nextLine();
            scanner.nextLine();
            String p=scanner.nextLine();
            for(User r:coder)
            {
                if(u.compareToIgnoreCase(r.username)==0 &&(p.compareTo(r.password)==0))
                    loggedInUser= r;
                else
                    loggedInUser=null;
            }
            if (loggedInUser != null) {
                System.out.println("User logged in Succesfully ,Welcome  "+loggedInUser.username);
                admin.showProjectForCoder();

            }else
            {
                System.out.println("Invalid username or password");
            }
        }
        return loggedInUser;
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User  user  = new User ();
        User loggedInUser=new User();
        Project  project  = new Project ();
        Admin admin = new Admin();
        ArrayList<User> coder=new ArrayList<>();
        ArrayList<User> buyer=new ArrayList<>();
        ArrayList<Admin> projectList=new ArrayList<>();

        while (true) {
            System.out.println("\n\nWelcome to Mingle Box\n1. Register\n2. Login\n3. Upload a Project\n4. Show all projects \n5. Price discussion\n6.  Log Out\n \nPlease select one option\n");
            int choice = scanner.nextInt();
            scanner.nextLine();

            char c;
            switch (choice) {
                case 1:
                    System.out.println("\nEnter 'a' for coder Registration and 'b' for buyer Registration:");
                    c=scanner.next().charAt(0);
                    if(c=='a'||c=='b') {
                        user.register(scanner,coder);
                        System.out.println(coder);
                    }
                    else {
                        user.register(scanner,buyer);
                        System.out.println(buyer);
                    }
                    System.out.println("Registration done");
                    break;
                case 2:
                    loggedInUser=user.login(coder,buyer,scanner,admin);
                    break;
                case 3:
                    String u,p;
                    System.out.println("\n Enter username and password for admin login ");
                    u=scanner.nextLine();
                    p=scanner.nextLine();

                    if (admin.checkAdmin(u,p)) {

                        System.out.println("\n Enter 'a' for project uploading ,'b' for project selling :");

                        c=scanner.next().charAt(0);
                        scanner.nextLine();
                        admin.uploadProject(scanner,c);
                        admin.showProjectForBuyer();
                        admin.showProjectForCoder();

                    }
                    else{
                        System.out.println("\n Invalid  credential :");
                    }
                    break;
                case 4:
                    loggedInUser= user.login(coder,buyer,scanner,admin);
                    System.out.println("welcome .."+loggedInUser.username);
                    admin.chooseProjectByCoder(loggedInUser,scanner);
                    break;
                case 5:
                    loggedInUser= user.login(coder,buyer,scanner,admin);
                    System.out.println("welcome .."+loggedInUser.username);
                    admin.chooseProjectBybuyer(loggedInUser,scanner);
                    break;
                case 6:
                    admin.approvedProject();
                case 7:
                    loggedInUser=null;
                    System.out.println("logged Out Succesfully");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}


