/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 const password = document.getElementById("password");
 const confPassword = document.getElementById("confpassword");
 const form = document.getElementById("form");
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const email = document.getElementById("email");
const username = document.getElementById("username");
const name = document.getElementById("name");

function checkPassword(passwd, confpasswd){
    var n = passwd.localeCompare(confpasswd);
    if(n===0) return true;
    return false;
}

//username 4-20
//passwd 5-32

form.addEventListener('submit', event =>{
        let n = checkPassword(password.value, confPassword.value);
        if(n === false){
            event.preventDefault();
            document.getElementById("confpassMsg").style.visibility = "visible";
            confPassword.value = "";
        }
        else if(password.value.length<5 || password.value.length>32){
            event.preventDefault();
            document.getElementById("passMsg").style.visibility = "visible";
        }
        if(emailRegex.test(email.value) === false){
            event.preventDefault();
            document.getElementById("emailMsg").style.visibility = "visible";
        }
        if(username.value.length<4 || username.value.length>20){
            event.preventDefault();
            document.getElementById("usernameMsg").style.visibility = 'visible';
        }
        if(name.value === ""){
             event.preventDefault();
             document.getElementById("nameMsg").style.visibility = 'visible';
        }
});

const confPassMSG = document.getElementById("confpassMsg");
confPassword.addEventListener('input', ()=>{
   confPassMSG.style.visibility = 'hidden'; 
});

password.addEventListener('input', ()=>{
   document.getElementById("passMsg").style.visibility = 'hidden'; 
});

email.addEventListener('input', () =>{
   document.getElementById("emailMsg").style.visibility = 'hidden'; 
    if(document.getElementById("duplicatedEmailMsg") !== null){
        document.getElementById("duplicatedEmailMsg") .style.display='none';
    }
});

username.addEventListener('input', () =>{
   document.getElementById("usernameMsg").style.visibility = 'hidden'; 
   document.getElementById("dupUser").remove();
});

name.addEventListener('input', () =>{
   document.getElementById("nameMsg").style.visibility = 'hidden'; 
});


//if(n === false){
//    
//}


