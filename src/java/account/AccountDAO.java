/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class AccountDAO {

    public static String validate(AccountDTO acc) throws Exception{  
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    String sql ="select * from Account where Email = ?";
    try{  
      con = ConnectDB.makeConnection();
      if (con != null) {
       pstm = con.prepareStatement(sql);
       pstm.setString(1,acc.getEmail());
       rs = pstm.executeQuery();
       if(rs.next())
       {
           System.out.println(acc.getPassword());
           System.out.println(rs.getString("Password"));
            if(acc.getPassword().equals(rs.getString("Password").trim()))
       {
            return rs.getString("Email");
       }
        }
        }
        }
      catch (Exception e) {
            e.printStackTrace();
      }
    finally
    {
        
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (con != null) {
                con.close();
            }
        
    }
    return null;
    
    }
}           
        

