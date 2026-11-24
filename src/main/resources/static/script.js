"use strict";

async function postData(url, data){
    await fetch(url,{
        method:"post",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify(data)
    }).then(response => {
        if (response.ok){
            document.querySelector("#output").innerHTML=`You have successfully created a new user
             named ${data.name}`;
        } else {
            document.querySelector("#output").innerHTML=`You have failed to create a user
             named ${data.name}. Please try again!!!`;
        }
    })
}

const form = document.querySelector("#createUserForm");
form.addEventListener("submit",async (event) => {
    event.preventDefault();
    const data = {"name":`${document.querySelector("#name").value}`,
    "email":`${document.querySelector("#email").value}`};

    await postData("http://localhost:8080/api/users",data);
})