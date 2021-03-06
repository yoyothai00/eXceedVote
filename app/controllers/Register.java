package controllers;

import com.avaje.ebean.Ebean;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;
import models.*;

public class Register extends Controller {
    
	public static Result main(){
		return redirect(routes.Application.main());
	}


	public  static Result addAccount(String username,String password,int type){
		Account a = new Account ();
        a.username=username;
        a.password=password;
        a.type=type;
    	a.save();


		return main();
	}

    public static Result getAddAccount (){
        Form<Account> newAccountForm = Form.form(Account.class).bindFromRequest();
        addAccount(newAccountForm.get().username, newAccountForm.get().password, newAccountForm.get().type);

        return main();

    }

    public static Result editInfo(String username,String name,String lastname,String studentID){
        Account newAccount=Account.findAccount(username);
        newAccount.name = name;
        newAccount.lastname = lastname;
        newAccount.studentID = studentID;
        newAccount.save();

        return main();
    }

    public static Result getEditTeam (){
        Form<Team> newAccountForm = Form.form(Team.class).bindFromRequest();

        Form<Account> newAccount = Form.form(Account.class).bindFromRequest();
        editInfo(session().get("username"),newAccount.get().name,newAccount.get().lastname,newAccount.get().studentID);
        //System.out.println(session().get("username"));
        //System.out.println(newAccountForm.get().team_name);
        editTeam(session().get("username"),newAccountForm.get().team_name);
        return main();

    }

    public static Result editTeam(String username,String team){


        Account newAccount=Account.findAccount(username);
        Team newTeam = Team.findTeam(team);

        newTeam.users.add(newAccount);
        newAccount.teams=newTeam;
        newAccount.save();
        newTeam.save();



        return main();


    }




 

}

