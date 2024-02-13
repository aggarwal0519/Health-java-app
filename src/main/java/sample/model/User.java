package sample.model;

public class User {
        private String firstname;
        private String lastname;
        private String username;
        private String password;

        private int id;

        public User() {
        }

        public User(String firstname, String lastname, String username, String password, int id) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.username = username;
            this.password = password;
            this.id = id;
        }

        public  int getId(){ return this.id;}
        public String getFirstname(){
            return this.firstname;
        }
        public String getLastname(){
            return this.lastname;
        }

        public String getUsername() {
            return this.username;
        }

        public String getPassword() {
            return this.password;
        }

    public void setFirstname(String firstname) {
        this.firstname = firstname;

    }
    public void setId(int id){ this.id = id;}
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


