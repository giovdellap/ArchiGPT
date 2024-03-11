
document.getElementById("contattiinvio").addEventListener("click",function(event){
    
    // Variabili associate ai campi del modulo
       var nome=document.getElementById("firstname").value;
       var email = document.getElementById("email").value;
       var testo = document.getElementById("text").value;

       var nome_valid = /^([A-Za-z ]{1,})+$/;
       if ((nome == "") || (nome == "undefined") || (!nome_valid.test(nome))) {
          alert("Devi inserire il tuo nome");
          console.log(1);
          
          return false;
       }
    
       var email_valid = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-]{2,})+.)+([a-zA-Z0-9]{2,})+$/;
       if (!email_valid.test(email) || (email == "") || (email == "undefined")) 
       {
          alert("Devi inserire un indirizzo mail corretto");
          return false;
       }
       
       if (testo == "" || testo == "undefined")
       {
         alert("Devi inserire un messaggio da inviarci!");
         return false;
       }



    })
   