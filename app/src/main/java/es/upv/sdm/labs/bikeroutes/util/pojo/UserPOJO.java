package es.upv.sdm.labs.bikeroutes.util.pojo;

import es.upv.sdm.labs.bikeroutes.enumerations.Gender;
import es.upv.sdm.labs.bikeroutes.model.User;
import es.upv.sdm.labs.bikeroutes.util.ImgSerializer;

/**
 * Created by Anderson on 12/04/2016.
 */
public class UserPOJO extends AbstractPOJO {

    private Users[] users;

    public UserPOJO(){}

    public UserPOJO(User user){
        this.users = new Users[1];
        Users u = users[0] = new Users();
        u.setId(user.getId());
        u.setName(user.getName());
        u.setDescription(user.getDescription());
        u.setGender(user.getGender().toString());
        u.setPassword(user.getPassword());
        u.setMail(user.getMail());
        if(user.getImage()!=null) u.setImage(ImgSerializer.serialize(user.getImage()));
        else u.setImage("");
    }

    public User getUser(){
        if(this.users==null || this.users.length==0) return null;
        Users u = users[0];
        User res = new User();
        res.setId(u.getId());
        res.setName(u.getName());
        res.setMail(u.getMail());
        res.setPassword(u.getPassword());
        res.setDescription(u.getDescription());
        res.setGender(Gender.getGender(u.getGender()));
        if(!u.getImage().isEmpty())res.setImage(ImgSerializer.deserialize(u.getImage()));
        return res;
    }

    public Users[] getUsers() {
        return users;
    }

    public void setUsers(Users[] users) {
        this.users = users;
    }

    public class Users{

        private int id;
        private String name;
        private String mail;
        private String password;
        private String description;
        private String gender;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
