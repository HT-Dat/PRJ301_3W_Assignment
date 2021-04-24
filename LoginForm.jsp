<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login </title>
    </head>
    
<body>
<form action="LoginServlet?action=login" method="POST">
  
		<table style="with: 50%">
 
			<tr>
				<td>Email</td>
				<td><input type="text" name="Email" /></td>
			</tr>
				<tr>
				<td>Password</td>
				<td><input type="password" name="Password" /></td>
			</tr>
		</table>
       <input type="submit" name="login"  />
</form>
</body>