
document.getElementById("sosinvio").addEventListener("click",function(event){
    
    // Variabili associate ai campi del modulo
       var nome=document.getElementById("firstname").value;
       var cognome=document.getElementById("lastname").value;
       var telefono = document.getElementById("tel").value;
       var email = document.getElementById("email").value;

       var nome_valid = /^([A-Za-z ]{1,})+$/;
       if ((nome == "") || (nome == "undefined") || (!nome_valid.test(nome))) {
          alert("Devi inserire un nome");
          console.log(1);
          
          return false;
       }
       var cognome_valid = /^([A-Za-z ]{1,})+$/;
       if ((cognome == "") || (cognome == "undefined") || (!cognome_valid.test(cognome))) {
        alert("Devi inserire un cognome");
        
        
        return false;
       }
       var tel_valid = /^([0-9]{9,10})+$/;
       if ((telefono == "") || (telefono == "undefined") || !tel_valid.test(telefono)) {
          alert("Devi inserire il telefono");
           telefono.value = "";
         
           return false;
       }
        
       var email_valid = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-]{2,})+.)+([a-zA-Z0-9]{2,})+$/;
       if (!email_valid.test(email) || (email == "") || (email == "undefined")) 
       {
          alert("Devi inserire un indirizzo mail corretto");
          return false;
       }
 
    })
   