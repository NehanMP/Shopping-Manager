public class User {
    String userName;
    String password;

   public User(String userName,String password){
        this.userName = userName;
        this.password = password;
   }

    //Getters
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }

    //Setters
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
