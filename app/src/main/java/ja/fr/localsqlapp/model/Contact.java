package ja.fr.localsqlapp.model;

public class Contact {
    private String name;
    private String firstName;
    private String email;
    private Long id;

    public Contact() {
    }

    public Contact(String name, String fisrtName, String email) {
        this.name = name;
        this.firstName = fisrtName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contact setFirstName(String fisrtName) {
        this.firstName = fisrtName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Contact setId(Long id) {
        this.id = id;
        return this;
    }
}
